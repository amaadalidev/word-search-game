package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import com.example.audio.SoundManager
import com.example.audio.TextToSpeechHelper
import com.example.data.GamePreferences
import com.example.engine.GeneratedBoard
import com.example.engine.WordDataBank
import com.example.engine.WordSearchGenerator
import com.example.model.ActiveSelection
import com.example.model.CellCoordinate
import com.example.model.Difficulty
import com.example.model.PlacedWord
import com.example.model.ThemeCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

enum class GameScreen {
    THEME_SELECT,
    GAME_PLAY
}

data class WordSearchUiState(
    val currentScreen: GameScreen = GameScreen.THEME_SELECT,
    val selectedDifficulty: Difficulty = Difficulty.MEDIUM,
    val selectedTheme: ThemeCategory = WordDataBank.categories.first(),
    val currentLevel: Int = 1,
    val board: GeneratedBoard? = null,
    val activeSelection: ActiveSelection? = null,
    val hintLetter: CellCoordinate? = null,
    val coins: Int = 300,
    val isLevelComplete: Boolean = false,
    val celebrationTitle: String = "AMAZING",
    val unlockedThemeIds: Set<String> = emptySet(),
    val themeToUnlock: ThemeCategory? = null,
    val infoMessage: String? = null,
    val vibrateEnabled: Boolean = true
)

class WordSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = GamePreferences(application)
    private val ttsHelper = TextToSpeechHelper(application)
    private val soundManager = SoundManager.getInstance(application)
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    private val _uiState = MutableStateFlow(
        WordSearchUiState(
            coins = preferences.coins,
            unlockedThemeIds = preferences.getUnlockedThemes(),
            vibrateEnabled = preferences.vibrateEnabled
        )
    )
    val uiState: StateFlow<WordSearchUiState> = _uiState.asStateFlow()

    init {
        // Load initial state
        refreshCoinsAndThemes()
    }

    private fun refreshCoinsAndThemes() {
        _uiState.update {
            it.copy(
                coins = preferences.coins,
                unlockedThemeIds = preferences.getUnlockedThemes(),
                vibrateEnabled = preferences.vibrateEnabled
            )
        }
    }

    fun selectDifficulty(difficulty: Difficulty) {
        soundManager.playClick()
        _uiState.update { it.copy(selectedDifficulty = difficulty) }
    }

    fun onThemeClicked(theme: ThemeCategory) {
        soundManager.playClick()
        if (preferences.isThemeUnlocked(theme.id)) {
            val savedLevel = preferences.getLevelProgress(theme.id, _uiState.value.selectedDifficulty.name)
            val levelToPlay = if (savedLevel in 1..theme.totalLevels) savedLevel + 1 else 1
            startLevel(theme, _uiState.value.selectedDifficulty, levelToPlay.coerceAtMost(theme.totalLevels))
        } else {
            // Prompt unlock
            _uiState.update { it.copy(themeToUnlock = theme) }
        }
    }

    fun selectThemeAndLevel(theme: ThemeCategory, level: Int) {
        soundManager.playClick()
        val clampedLevel = level.coerceIn(1, theme.totalLevels)
        startLevel(theme, _uiState.value.selectedDifficulty, clampedLevel)
    }

    fun dismissUnlockDialog() {
        soundManager.playClick()
        _uiState.update { it.copy(themeToUnlock = null) }
    }

    fun confirmUnlockTheme(theme: ThemeCategory) {
        if (preferences.spendCoins(theme.unlockCost)) {
            preferences.unlockTheme(theme.id)
            vibrateShort()
            soundManager.playSparkle()
            _uiState.update {
                it.copy(
                    coins = preferences.coins,
                    unlockedThemeIds = preferences.getUnlockedThemes(),
                    themeToUnlock = null,
                    infoMessage = "Unlocked ${theme.title}!"
                )
            }
            startLevel(theme, _uiState.value.selectedDifficulty, 1)
        } else {
            soundManager.playClick()
            _uiState.update {
                it.copy(
                    themeToUnlock = null,
                    infoMessage = "Not enough coins! Play other levels to earn more."
                )
            }
        }
    }

    fun startLevel(theme: ThemeCategory, difficulty: Difficulty, level: Int) {
        val targetWords = WordDataBank.getWordsForLevel(theme.id, difficulty, level)
        val generatedBoard = WordSearchGenerator.generate(difficulty, targetWords)

        _uiState.update {
            it.copy(
                currentScreen = GameScreen.GAME_PLAY,
                selectedTheme = theme,
                selectedDifficulty = difficulty,
                currentLevel = level,
                board = generatedBoard,
                activeSelection = null,
                hintLetter = null,
                isLevelComplete = false,
                celebrationTitle = listOf("AMAZING", "BRILLIANT", "EXCELLENT", "FANTASTIC").random()
            )
        }
    }

    fun backToThemeSelect() {
        soundManager.playClick()
        refreshCoinsAndThemes()
        _uiState.update {
            it.copy(
                currentScreen = GameScreen.THEME_SELECT,
                activeSelection = null,
                hintLetter = null,
                isLevelComplete = false
            )
        }
    }

    fun restartCurrentLevel() {
        soundManager.playClick()
        val currentTheme = _uiState.value.selectedTheme
        val currentDiff = _uiState.value.selectedDifficulty
        val currentLvl = _uiState.value.currentLevel
        startLevel(currentTheme, currentDiff, currentLvl)
        vibrateShort()
    }

    fun nextLevel() {
        soundManager.playClick()
        val currentTheme = _uiState.value.selectedTheme
        val currentDiff = _uiState.value.selectedDifficulty
        val nextLvl = (_uiState.value.currentLevel + 1).coerceAtMost(currentTheme.totalLevels)
        startLevel(currentTheme, currentDiff, nextLvl)
    }

    fun onSelectionStart(start: CellCoordinate) {
        val board = _uiState.value.board ?: return
        if (start.row !in 0 until board.size || start.col !in 0 until board.size) return

        soundManager.playTileSelect(0)
        val char = board.grid[start.row][start.col].toString()
        _uiState.update {
            it.copy(
                activeSelection = ActiveSelection(
                    start = start,
                    current = start,
                    cells = listOf(start),
                    formedWord = char
                )
            )
        }
    }

    fun onSelectionMove(current: CellCoordinate) {
        val board = _uiState.value.board ?: return
        val start = _uiState.value.activeSelection?.start ?: return
        if (current.row !in 0 until board.size || current.col !in 0 until board.size) return

        val clampedCells = computeStraightLine(start, current, board.size)
        val prevSelection = _uiState.value.activeSelection
        val prevLastCell = prevSelection?.cells?.lastOrNull()
        val newLastCell = clampedCells.lastOrNull()

        if (clampedCells.size != (prevSelection?.cells?.size ?: 0) || newLastCell != prevLastCell) {
            soundManager.playTileSelect(clampedCells.size - 1)
        }

        val formed = clampedCells.map { board.grid[it.row][it.col] }.joinToString("")

        _uiState.update {
            it.copy(
                activeSelection = ActiveSelection(
                    start = start,
                    current = current,
                    cells = clampedCells,
                    formedWord = formed
                )
            )
        }
    }

    fun onSelectionEnd() {
        val selection = _uiState.value.activeSelection ?: return
        val board = _uiState.value.board ?: return
        val currentTheme = _uiState.value.selectedTheme
        val currentDiff = _uiState.value.selectedDifficulty
        val currentLvl = _uiState.value.currentLevel

        val wordForward = selection.formedWord
        val wordBackward = selection.formedWord.reversed()

        var matchedIndex = -1
        for (i in board.placedWords.indices) {
            val pw = board.placedWords[i]
            if (!pw.isFound && (pw.word.equals(wordForward, ignoreCase = true) || pw.word.equals(wordBackward, ignoreCase = true))) {
                matchedIndex = i
                break
            }
        }

        if (matchedIndex != -1) {
            // Found a word!
            val foundWord = board.placedWords[matchedIndex].word
            soundManager.playWordMatch()
            ttsHelper.speak(foundWord)
            vibrateSuccess()
            preferences.addCoins(10)

            val updatedPlacedWords = board.placedWords.mapIndexed { idx, pw ->
                if (idx == matchedIndex) pw.copy(isFound = true) else pw
            }

            val allFound = updatedPlacedWords.all { it.isFound }
            val updatedBoard = board.copy(placedWords = updatedPlacedWords)

            if (allFound) {
                // Completed level!
                soundManager.playLevelComplete()
                preferences.addCoins(currentDiff.rewardCoins)
                val currentProgress = preferences.getLevelProgress(currentTheme.id, currentDiff.name)
                if (currentLvl > currentProgress) {
                    preferences.setLevelProgress(currentTheme.id, currentDiff.name, currentLvl)
                }
            }

            _uiState.update {
                it.copy(
                    board = updatedBoard,
                    activeSelection = null,
                    hintLetter = null,
                    coins = preferences.coins,
                    isLevelComplete = allFound,
                    celebrationTitle = if (allFound) listOf("AMAZING", "EXCELLENT", "BRILLIANT", "AWESOME").random() else it.celebrationTitle
                )
            }
        } else {
            // No match
            _uiState.update { it.copy(activeSelection = null) }
        }
    }

    fun useMagicWandHint() {
        val board = _uiState.value.board ?: return
        val unfoundWords = board.placedWords.filter { !it.isFound }
        if (unfoundWords.isEmpty()) return

        val cost = 20
        if (preferences.spendCoins(cost)) {
            vibrateShort()
            soundManager.playSparkle()
            val target = unfoundWords.random()
            val firstCell = CellCoordinate(target.startRow, target.startCol)
            _uiState.update {
                it.copy(
                    coins = preferences.coins,
                    hintLetter = firstCell,
                    infoMessage = "First letter highlighted!"
                )
            }
        } else {
            soundManager.playClick()
            _uiState.update { it.copy(infoMessage = "Need 20 coins for Magic Wand hint!") }
        }
    }

    fun useMagnifierReveal() {
        val board = _uiState.value.board ?: return
        val unfoundWords = board.placedWords.filter { !it.isFound }
        if (unfoundWords.isEmpty()) return

        val cost = 50
        if (preferences.spendCoins(cost)) {
            vibrateSuccess()
            soundManager.playSparkle()
            val target = unfoundWords.random()
            ttsHelper.speak(target.word)
            val updatedPlaced = board.placedWords.map {
                if (it.word == target.word) it.copy(isFound = true) else it
            }
            val allFound = updatedPlaced.all { it.isFound }
            val updatedBoard = board.copy(placedWords = updatedPlaced)

            if (allFound) {
                soundManager.playLevelComplete()
                preferences.addCoins(_uiState.value.selectedDifficulty.rewardCoins)
                val currentProgress = preferences.getLevelProgress(_uiState.value.selectedTheme.id, _uiState.value.selectedDifficulty.name)
                if (_uiState.value.currentLevel > currentProgress) {
                    preferences.setLevelProgress(_uiState.value.selectedTheme.id, _uiState.value.selectedDifficulty.name, _uiState.value.currentLevel)
                }
            }

            _uiState.update {
                it.copy(
                    board = updatedBoard,
                    coins = preferences.coins,
                    hintLetter = null,
                    isLevelComplete = allFound,
                    infoMessage = "Word '${target.word}' revealed!"
                )
            }
        } else {
            soundManager.playClick()
            _uiState.update { it.copy(infoMessage = "Need 50 coins to reveal a word!") }
        }
    }

    fun clearInfoMessage() {
        _uiState.update { it.copy(infoMessage = null) }
    }

    fun getThemeProgress(themeId: String, difficulty: Difficulty): Int {
        return preferences.getLevelProgress(themeId, difficulty.name)
    }

    private fun computeStraightLine(start: CellCoordinate, current: CellCoordinate, gridSize: Int): List<CellCoordinate> {
        val dR = current.row - start.row
        val dC = current.col - start.col

        if (dR == 0 && dC == 0) return listOf(start)

        val absR = abs(dR)
        val absC = abs(dC)

        val stepR: Int
        val stepC: Int
        val steps: Int

        when {
            // Horizontal
            absC >= 2 * absR -> {
                stepR = 0
                stepC = sign(dC.toDouble()).toInt()
                steps = absC
            }
            // Vertical
            absR >= 2 * absC -> {
                stepR = sign(dR.toDouble()).toInt()
                stepC = 0
                steps = absR
            }
            // Diagonal (approximate 45 degrees)
            else -> {
                stepR = sign(dR.toDouble()).toInt()
                stepC = sign(dC.toDouble()).toInt()
                steps = max(absR, absC)
            }
        }

        val cells = mutableListOf<CellCoordinate>()
        for (i in 0..steps) {
            val r = start.row + i * stepR
            val c = start.col + i * stepC
            if (r in 0 until gridSize && c in 0 until gridSize) {
                cells.add(CellCoordinate(r, c))
            } else {
                break
            }
        }
        return if (cells.isNotEmpty()) cells else listOf(start)
    }

    private fun vibrateShort() {
        if (!preferences.vibrateEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(30)
            }
        } catch (_: Exception) {}
    }

    private fun vibrateSuccess() {
        if (!preferences.vibrateEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(
                    VibrationEffect.createWaveform(longArrayOf(0, 40, 60, 80), intArrayOf(0, 150, 0, 255), -1)
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(100)
            }
        } catch (_: Exception) {}
    }

    fun speakWord(word: String) {
        soundManager.playClick()
        ttsHelper.speak(word)
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
        ttsHelper.shutdown()
    }
}
