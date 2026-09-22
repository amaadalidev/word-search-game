package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.audio.SoundManager
import com.example.model.CellCoordinate
import com.example.ui.components.BottomGameActions
import com.example.ui.components.HeaderPillBanner
import com.example.ui.components.LevelCelebrationOverlay
import com.example.ui.components.WordBankView
import com.example.ui.components.WordSearchGrid
import com.example.viewmodel.WordSearchUiState
import kotlinx.coroutines.delay

@Composable
fun GamePlayScreen(
    uiState: WordSearchUiState,
    onBackToThemes: () -> Unit,
    onSelectionStart: (CellCoordinate) -> Unit,
    onSelectionMove: (CellCoordinate) -> Unit,
    onSelectionEnd: () -> Unit,
    onMagicWandHint: () -> Unit,
    onMagnifierReveal: () -> Unit,
    onRestartLevel: () -> Unit,
    onNextLevel: () -> Unit,
    onClearInfoMessage: () -> Unit,
    onWordClick: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val board = uiState.board ?: return
    val theme = uiState.selectedTheme
    val difficulty = uiState.selectedDifficulty
    val context = LocalContext.current
    val soundManager = remember(context) { SoundManager.getInstance(context) }

    var showExitDialog by remember { mutableStateOf(false) }

    // Intercept system/device back button
    BackHandler(enabled = !uiState.isLevelComplete) {
        showExitDialog = true
    }

    LaunchedEffect(uiState.infoMessage) {
        if (uiState.infoMessage != null) {
            delay(2500)
            onClearInfoMessage()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background Wallpaper
        Image(
            painter = painterResource(id = R.drawable.img_scenic_bg),
            contentDescription = "Scenic Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient tint overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x55000000),
                            Color(0x33000000),
                            Color(0x88000000)
                        )
                    )
                )
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentAlignment = Alignment.TopCenter
        ) {
            val screenWidth = maxWidth
            val isTablet = screenWidth > 600.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 560.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Navigation Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = if (isTablet) 20.dp else 10.dp,
                                vertical = 6.dp
                            ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .shadow(3.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.92f))
                                .clickable {
                                    soundManager.playClick()
                                    if (uiState.isLevelComplete) {
                                        onBackToThemes()
                                    } else {
                                        showExitDialog = true
                                    }
                                }
                                .testTag("game_back_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to Themes",
                                tint = Color(0xFF333333),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        // Level & Difficulty Badge (Fills middle with ellipsis on overflow)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.92f))
                                .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(20.dp))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${theme.title.uppercase()} • LVL ${uiState.currentLevel}",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF263238),
                                letterSpacing = 0.3.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Points / Coins Badge (Guaranteed full width and never compressed)
                        Box(
                            modifier = Modifier
                                .shadow(3.dp, RoundedCornerShape(20.dp))
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFFFF9C4))
                                .border(1.5.dp, Color(0xFFFFB300), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("coins_counter"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = "🪙", fontSize = 14.sp)
                                Text(
                                    text = "${uiState.coins}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFE65100),
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Word Bank Card (shows category banner and words)
                    WordBankView(
                        theme = theme,
                        placedWords = board.placedWords,
                        onWordClick = onWordClick
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // The Word Search Grid
                    WordSearchGrid(
                        board = board,
                        activeSelection = uiState.activeSelection,
                        hintLetter = uiState.hintLetter,
                        onSelectionStart = onSelectionStart,
                        onSelectionMove = onSelectionMove,
                        onSelectionEnd = onSelectionEnd
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bottom Actions (Star, Wand Hint, Magnifier Reveal, Refresh)
                BottomGameActions(
                    themeColor = theme.accentColor,
                    onStarClick = {
                        if (uiState.isLevelComplete) {
                            onBackToThemes()
                        } else {
                            showExitDialog = true
                        }
                    },
                    onHintClick = onMagicWandHint,
                    onRevealClick = onMagnifierReveal,
                    onRestartClick = onRestartLevel
                )
            }
        }

        // Exit Confirmation Dialog
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning",
                        tint = theme.accentColor,
                        modifier = Modifier.size(36.dp)
                    )
                },
                title = {
                    Text(
                        text = "Leave Game?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF212121)
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to go back to the menu? Your current puzzle progress will be lost.",
                        fontSize = 15.sp,
                        color = Color(0xFF555555),
                        lineHeight = 20.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            soundManager.playClick()
                            showExitDialog = false
                            onBackToThemes()
                        },
                        modifier = Modifier.testTag("confirm_exit_button")
                    ) {
                        Text(
                            text = "Leave",
                            color = Color(0xFFE53935),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            soundManager.playClick()
                            showExitDialog = false
                        },
                        modifier = Modifier.testTag("cancel_exit_button")
                    ) {
                        Text(
                            text = "Keep Playing",
                            color = theme.accentColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                },
                shape = RoundedCornerShape(20.dp),
                containerColor = Color.White
            )
        }

        // Temporary Info Toast
        AnimatedVisibility(
            visible = uiState.infoMessage != null,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xE6212121))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = uiState.infoMessage ?: "",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Celebration Overlay ("AMAZING" banner matching screenshot 3)
        if (uiState.isLevelComplete) {
            LevelCelebrationOverlay(
                title = uiState.celebrationTitle,
                coinsEarned = difficulty.rewardCoins,
                onNextLevel = onNextLevel,
                onChooseTheme = onBackToThemes
            )
        }
    }
}
