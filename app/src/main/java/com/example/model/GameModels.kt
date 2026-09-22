package com.example.model

import androidx.compose.ui.graphics.Color

enum class Difficulty(
    val title: String,
    val gridSize: Int,
    val wordCount: Int,
    val rewardCoins: Int
) {
    EASY("EASY", 6, 5, 25),
    MEDIUM("MEDIUM", 7, 6, 35),
    HARD("HARD", 8, 7, 50),
    PRO("PRO", 9, 8, 75);

    fun getAllowedDirections(): List<Direction> {
        return when (this) {
            EASY -> listOf(Direction.RIGHT, Direction.DOWN)
            MEDIUM -> listOf(Direction.RIGHT, Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_LEFT)
            HARD, PRO -> Direction.values().toList()
        }
    }
}

enum class Direction(val dRow: Int, val dCol: Int) {
    RIGHT(0, 1),
    DOWN(1, 0),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(1, -1),
    LEFT(0, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    UP_LEFT(-1, -1)
}

enum class ThemeIcon {
    ANIMALS,
    COLORS,
    CITIES,
    NATURE,
    HOUSE,
    ADJECTIVES,
    TV_SHOWS,
    COUNTRIES,
    MONUMENTS,
    ACTORS,
    WRITERS,
    HISTORY,
    SPACE,
    FOOD,
    SPORTS,
    SCIENCE,
    VOCABULARY
}

data class ThemeCategory(
    val id: String,
    val title: String,
    val iconType: ThemeIcon,
    val unlockCost: Int = 0,
    val totalLevels: Int = 30,
    val accentColor: Color = Color(0xFF4CAF50)
)

data class CellCoordinate(val row: Int, val col: Int) {
    override fun toString(): String = "($row, $col)"
}

data class PlacedWord(
    val word: String,
    val startRow: Int,
    val startCol: Int,
    val endRow: Int,
    val endCol: Int,
    val colorIndex: Int,
    var isFound: Boolean = false
) {
    fun getCells(): List<CellCoordinate> {
        val cells = mutableListOf<CellCoordinate>()
        val len = word.length
        val dRow = if (len > 1) (endRow - startRow) / (len - 1) else 0
        val dCol = if (len > 1) (endCol - startCol) / (len - 1) else 0

        for (i in 0 until len) {
            cells.add(CellCoordinate(startRow + i * dRow, startCol + i * dCol))
        }
        return cells
    }
}

data class ActiveSelection(
    val start: CellCoordinate,
    val current: CellCoordinate,
    val cells: List<CellCoordinate>,
    val formedWord: String
)

data class WordCapsuleColor(
    val fill: Color,
    val border: Color,
    val textColor: Color
)

object WordSearchPalette {
    val capsules = listOf(
        WordCapsuleColor(Color(0x88FF4081), Color(0xFFFF4081), Color(0xFF880E4F)), // Pink
        WordCapsuleColor(Color(0x882979FF), Color(0xFF2979FF), Color(0xFF0D47A1)), // Blue
        WordCapsuleColor(Color(0x8800E676), Color(0xFF00E676), Color(0xFF1B5E20)), // Light Green
        WordCapsuleColor(Color(0x88FF9100), Color(0xFFFF9100), Color(0xFFE65100)), // Orange
        WordCapsuleColor(Color(0x88FFD600), Color(0xFFFFD600), Color(0xFFF57F17)), // Yellow
        WordCapsuleColor(Color(0x88AA00FF), Color(0xFFAA00FF), Color(0xFF4A148C)), // Violet
        WordCapsuleColor(Color(0x8800E5FF), Color(0xFF00E5FF), Color(0xFF006064)), // Cyan
        WordCapsuleColor(Color(0x88FF5252), Color(0xFFFF5252), Color(0xFFB71C1C))  // Red/Coral
    )
}
