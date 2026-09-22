package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.GamePlayScreen
import com.example.ui.screens.ThemeSelectScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.GameScreen
import com.example.viewmodel.WordSearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WordSearchApp()
                }
            }
        }
    }
}

@Composable
fun WordSearchApp(viewModel: WordSearchViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle system back button during gameplay
    BackHandler(enabled = uiState.currentScreen == GameScreen.GAME_PLAY) {
        viewModel.backToThemeSelect()
    }

    when (uiState.currentScreen) {
        GameScreen.THEME_SELECT -> {
            ThemeSelectScreen(
                uiState = uiState,
                onDifficultySelected = { viewModel.selectDifficulty(it) },
                onThemeSelected = { viewModel.onThemeClicked(it) },
                onSelectThemeLevel = { theme, level -> viewModel.selectThemeAndLevel(theme, level) },
                onConfirmUnlock = { viewModel.confirmUnlockTheme(it) },
                onDismissUnlock = { viewModel.dismissUnlockDialog() },
                onSpeakWord = { viewModel.speakWord(it) },
                getProgress = { themeId, diff -> viewModel.getThemeProgress(themeId, diff) }
            )
        }
        GameScreen.GAME_PLAY -> {
            GamePlayScreen(
                uiState = uiState,
                onBackToThemes = { viewModel.backToThemeSelect() },
                onSelectionStart = { viewModel.onSelectionStart(it) },
                onSelectionMove = { viewModel.onSelectionMove(it) },
                onSelectionEnd = { viewModel.onSelectionEnd() },
                onMagicWandHint = { viewModel.useMagicWandHint() },
                onMagnifierReveal = { viewModel.useMagnifierReveal() },
                onRestartLevel = { viewModel.restartCurrentLevel() },
                onNextLevel = { viewModel.nextLevel() },
                onClearInfoMessage = { viewModel.clearInfoMessage() },
                onWordClick = { viewModel.speakWord(it) }
            )
        }
    }
}
