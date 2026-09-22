package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.graphics.StrokeCap
import com.example.model.Difficulty
import com.example.model.ThemeCategory

@Composable
fun LevelCelebrationOverlay(
    title: String,
    coinsEarned: Int,
    onNextLevel: () -> Unit,
    onChooseTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // "AMAZING" Banner matching screenshot 3
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFFFF9100),
                                Color(0xFFFFAB00),
                                Color(0xFFFFD600),
                                Color(0xFFFFAB00),
                                Color(0xFFFF9100)
                            )
                        )
                    )
                    .border(3.dp, Color.White, RoundedCornerShape(20.dp))
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                    Text(
                        text = " $title! ",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Star rating row
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD600),
                        modifier = Modifier
                            .size(46.dp)
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bonus reward chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF2E7D32))
                    .border(2.dp, Color(0xFF81C784), RoundedCornerShape(24.dp))
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "+$coinsEarned COINS EARNED 🪙",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Buttons
            Button(
                onClick = onNextLevel,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(54.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp))
                    .testTag("celebration_next_level_button"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6D00)
                )
            ) {
                Text(
                    text = "NEXT LEVEL",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onChooseTheme,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(48.dp)
                    .testTag("celebration_choose_theme_button"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                Text(
                    text = "EXPLORE THEMES",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )
            }
        }
    }
}

@Composable
fun UnlockThemeDialog(
    theme: ThemeCategory,
    userCoins: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val canAfford = userCoins >= theme.unlockCost

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF7))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(theme.accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    ThemeCategoryIcon(
                        icon = theme.iconType,
                        tint = theme.accentColor,
                        size = 50.dp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Unlock ${theme.title}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF2E1C00),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Unlock 30 challenging levels with custom words in ${theme.title}!",
                    fontSize = 14.sp,
                    color = Color(0xFF6D4C41),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFECB3))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Cost: ${theme.unlockCost} 🪙",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFBF360C)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(
                        text = "(You have: $userCoins 🪙)",
                        fontSize = 13.sp,
                        color = Color(0xFF5D4037)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onConfirm,
                    enabled = canAfford,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("unlock_confirm_button"),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canAfford) Color(0xFF4CAF50) else Color.Gray
                    )
                ) {
                    Text(
                        text = if (canAfford) "UNLOCK NOW" else "NOT ENOUGH COINS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeLevelSelectDialog(
    theme: ThemeCategory,
    difficulty: Difficulty,
    completedLevels: Int,
    onSelectLevel: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val totalLevels = theme.totalLevels
    val currentPlayableLevel = (completedLevels + 1).coerceAtMost(totalLevels)

    val pageSize = 30
    val maxPage = ((totalLevels - 1) / pageSize).coerceAtLeast(0)
    var selectedPage by remember {
        mutableIntStateOf(((currentPlayableLevel - 1) / pageSize).coerceIn(0, maxPage))
    }

    val startLevel = selectedPage * pageSize + 1
    val endLevel = ((selectedPage + 1) * pageSize).coerceAtMost(totalLevels)
    val displayedLevels = (startLevel..endLevel).toList()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF7))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemeCategoryIcon(
                            icon = theme.iconType,
                            tint = theme.accentColor,
                            size = 28.dp
                        )
                        Column {
                            Text(
                                text = theme.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF2E1C00)
                            )
                            Text(
                                text = if (theme.id == "vocabulary") "${difficulty.title} • 120,000 LEVELS" else "${difficulty.title} • $totalLevels LEVELS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.accentColor
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("close_level_select_dialog")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Overall Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { (completedLevels.toFloat() / totalLevels).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF4CAF50),
                        trackColor = Color(0xFFE0E0E0),
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = "$completedLevels/$totalLevels Done",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Pagination Navigation Row with Left & Right Icons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF3E5F5).copy(alpha = 0.5f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (selectedPage > 0) {
                                selectedPage--
                            }
                        },
                        enabled = selectedPage > 0,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedPage > 0) theme.accentColor.copy(alpha = 0.15f)
                                else Color.LightGray.copy(alpha = 0.2f)
                            )
                            .testTag("prev_level_page_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous 30 Levels",
                            tint = if (selectedPage > 0) theme.accentColor else Color.Gray.copy(alpha = 0.4f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Levels $startLevel – $endLevel",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF263238)
                        )
                        if (maxPage > 0) {
                            Text(
                                text = "Page ${selectedPage + 1} of ${maxPage + 1}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF78909C)
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            if (selectedPage < maxPage) {
                                selectedPage++
                            }
                        },
                        enabled = selectedPage < maxPage,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedPage < maxPage) theme.accentColor.copy(alpha = 0.15f)
                                else Color.LightGray.copy(alpha = 0.2f)
                            )
                            .testTag("next_level_page_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next 30 Levels",
                            tint = if (selectedPage < maxPage) theme.accentColor else Color.Gray.copy(alpha = 0.4f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Grid of 30 Levels (6 columns x 5 rows)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(6),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                        .testTag("level_select_grid"),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(displayedLevels) { lvl ->
                        val isCompleted = lvl <= completedLevels
                        val isCurrent = lvl == currentPlayableLevel

                        val bgColor = when {
                            isCurrent -> theme.accentColor
                            isCompleted -> Color(0xFFE8F5E9)
                            else -> Color(0xFFF5F5F5)
                        }

                        val textColor = when {
                            isCurrent -> Color.White
                            isCompleted -> Color(0xFF2E7D32)
                            else -> Color(0xFF616161)
                        }

                        val borderColor = when {
                            isCurrent -> Color(0xFFFFB300)
                            isCompleted -> Color(0xFFA5D6A7)
                            else -> Color(0xFFE0E0E0)
                        }

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(bgColor)
                                .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
                                .clickable {
                                    onSelectLevel(lvl)
                                }
                                .testTag("level_button_$lvl"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$lvl",
                                    fontSize = if (lvl >= 10000) 9.sp else if (lvl >= 1000) 10.sp else 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = textColor
                                )
                                if (isCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFB300),
                                        modifier = Modifier.size(9.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Primary Quick Action: Play Next Unlocked Level
                Button(
                    onClick = { onSelectLevel(currentPlayableLevel) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("play_current_level_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.accentColor)
                ) {
                    Text(
                        text = if (completedLevels >= totalLevels) "REPLAY LEVEL 1" else "PLAY LEVEL $currentPlayableLevel",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }
        }
    }
}
