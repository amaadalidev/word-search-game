package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.engine.WordDataBank
import com.example.model.Difficulty
import com.example.model.ThemeCategory
import com.example.ui.components.DifficultyTabs
import com.example.ui.components.ThemeCard
import com.example.ui.components.ThemeLevelSelectDialog
import com.example.ui.components.UnlockThemeDialog
import com.example.ui.components.VocabularyCard
import com.example.ui.components.VocabularyDictionaryDialog
import com.example.viewmodel.WordSearchUiState

@Composable
fun ThemeSelectScreen(
    uiState: WordSearchUiState,
    onDifficultySelected: (Difficulty) -> Unit,
    onThemeSelected: (ThemeCategory) -> Unit,
    onSelectThemeLevel: (ThemeCategory, Int) -> Unit,
    onConfirmUnlock: (ThemeCategory) -> Unit,
    onDismissUnlock: () -> Unit,
    onSpeakWord: (String) -> Unit,
    getProgress: (String, Difficulty) -> Int,
    modifier: Modifier = Modifier
) {
    var showVocabularyDialog by remember { mutableStateOf(false) }
    var selectedThemeForLevels by remember { mutableStateOf<ThemeCategory?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        // Alpine lake & mountain background
        Image(
            painter = painterResource(id = R.drawable.img_scenic_bg),
            contentDescription = "Scenic Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Soft ambient overlay to ensure optimal contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x33B3E5FC),
                            Color(0x1AFFFFFF),
                            Color(0x55000000)
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
            val isTablet = screenWidth >= 600.dp
            val columnsCount = when {
                screenWidth < 360.dp -> 2
                screenWidth < 600.dp -> 3
                screenWidth < 840.dp -> 4
                else -> 5
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columnsCount),
                contentPadding = PaddingValues(
                    start = if (isTablet) 24.dp else 10.dp,
                    end = if (isTablet) 24.dp else 10.dp,
                    top = 8.dp,
                    bottom = 20.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 760.dp)
                    .testTag("themes_grid")
            ) {
                // 1. Top Header Row: Coins Wallet (Left) & Word Search Puzzle Search Pill (Right)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: Golden Coin Balance Pill with green plus button
                        Box(
                            modifier = Modifier
                                .shadow(4.dp, RoundedCornerShape(22.dp))
                                .clip(RoundedCornerShape(22.dp))
                                .background(Color(0xFFFFF9C4))
                                .border(1.5.dp, Color(0xFFFFD54F), RoundedCornerShape(22.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .testTag("coins_counter"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // 3D Coin with Star
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .shadow(2.dp, CircleShape)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.radialGradient(
                                                listOf(Color(0xFFFFEE58), Color(0xFFFFB300), Color(0xFFF57F17))
                                            )
                                        )
                                        .border(1.dp, Color(0xFFFFD54F), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFE65100),
                                        modifier = Modifier.size(13.dp)
                                    )
                                }

                                Text(
                                    text = "${uiState.coins}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF3E2723)
                                )

                                // Green plus button
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF4CAF50))
                                        .border(1.dp, Color.White, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Coins",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }

                        // Right: WORD SEARCH PUZZLE Search Pill
                        Box(
                            modifier = Modifier
                                .shadow(4.dp, RoundedCornerShape(22.dp))
                                .clip(RoundedCornerShape(22.dp))
                                .background(Color.White.copy(alpha = 0.92f))
                                .border(1.dp, Color(0xFFB2EBF2), RoundedCornerShape(22.dp))
                                .clickable { showVocabularyDialog = true }
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFF0D47A1),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "WORD SEARCH PUZZLE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF0D47A1),
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }

                // 2. 3D Game Logo Banner
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_home_logo_1790094759106),
                            contentDescription = "Word Search Puzzle Logo",
                            modifier = Modifier
                                .fillMaxWidth(if (isTablet) 0.65f else 0.90f)
                                .height(if (isTablet) 140.dp else 115.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                // 3. Featured Vocabulary Game Card (Daily Puzzle)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    VocabularyCard(
                        onPlayVocabularyGame = {
                            selectedThemeForLevels = WordDataBank.vocabularyCategory
                        },
                        onBrowseDictionary = {
                            showVocabularyDialog = true
                        }
                    )
                }

                // 4. Difficulty Switcher Tabs (EASY, MEDIUM, HARD, 👑 PRO)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    DifficultyTabs(
                        selectedDifficulty = uiState.selectedDifficulty,
                        onDifficultySelected = onDifficultySelected
                    )
                }

                // 5. Theme Cards (3x3 Grid on mobile, scalable for tablet)
                items(WordDataBank.categories) { theme ->
                    val isUnlocked = uiState.unlockedThemeIds.contains(theme.id) || theme.unlockCost == 0
                    val progress = getProgress(theme.id, uiState.selectedDifficulty)

                    ThemeCard(
                        theme = theme,
                        progress = progress,
                        isUnlocked = isUnlocked,
                        onClick = {
                            if (isUnlocked) {
                                selectedThemeForLevels = theme
                            } else {
                                onThemeSelected(theme)
                            }
                        }
                    )
                }
            }
        }

        // Unlock Theme Confirmation Dialog
        if (uiState.themeToUnlock != null) {
            UnlockThemeDialog(
                theme = uiState.themeToUnlock,
                userCoins = uiState.coins,
                onConfirm = { onConfirmUnlock(uiState.themeToUnlock) },
                onDismiss = onDismissUnlock
            )
        }

        // 30-Level Pagination Dialog for Selected Theme
        selectedThemeForLevels?.let { theme ->
            val progress = getProgress(theme.id, uiState.selectedDifficulty)
            ThemeLevelSelectDialog(
                theme = theme,
                difficulty = uiState.selectedDifficulty,
                completedLevels = progress,
                onSelectLevel = { level ->
                    selectedThemeForLevels = null
                    onSelectThemeLevel(theme, level)
                },
                onDismiss = { selectedThemeForLevels = null }
            )
        }

        // Full-Screen Vocabulary Dictionary Dialog
        if (showVocabularyDialog) {
            VocabularyDictionaryDialog(
                onDismiss = { showVocabularyDialog = false },
                onSpeakWord = onSpeakWord
            )
        }
    }
}
