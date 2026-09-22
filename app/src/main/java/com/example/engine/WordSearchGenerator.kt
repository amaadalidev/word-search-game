package com.example.engine

import com.example.model.CellCoordinate
import com.example.model.Difficulty
import com.example.model.Direction
import com.example.model.PlacedWord
import kotlin.random.Random

data class GeneratedBoard(
    val size: Int,
    val grid: List<List<Char>>,
    val placedWords: List<PlacedWord>
)

object WordSearchGenerator {

    private const val LETTER_POOL =
        "EEEEEEEEETTTTTTAAAAAOOOOIIINNNSSSHHHRRRDDDLLCCCUUUMMWWFFGGYYPPBVKJXQZ"

    fun generate(
        difficulty: Difficulty,
        targetWords: List<String>,
        random: Random = Random.Default
    ): GeneratedBoard {
        val size = difficulty.gridSize
        val allowedDirections = difficulty.getAllowedDirections()

        // Sort words by length descending for better packing
        val sortedWords = targetWords.sortedByDescending { it.length }

        // Attempt up to 20 times to place all or most words
        var bestPlaced = emptyList<PlacedWord>()
        var bestGrid = Array(size) { CharArray(size) { ' ' } }

        for (attempt in 0 until 30) {
            val grid = Array(size) { CharArray(size) { ' ' } }
            val placed = mutableListOf<PlacedWord>()

            var colorCounter = 0
            for (word in sortedWords) {
                val cleanWord = word.uppercase().filter { it in 'A'..'Z' }
                if (cleanWord.length > size) continue

                val placement = tryPlaceWord(grid, size, cleanWord, allowedDirections, random)
                if (placement != null) {
                    val (startRow, startCol, endRow, endCol) = placement
                    commitWord(grid, cleanWord, startRow, startCol, endRow, endCol)
                    placed.add(
                        PlacedWord(
                            word = cleanWord,
                            startRow = startRow,
                            startCol = startCol,
                            endRow = endRow,
                            endCol = endCol,
                            colorIndex = colorCounter % 8,
                            isFound = false
                        )
                    )
                    colorCounter++
                }
            }

            if (placed.size > bestPlaced.size) {
                bestPlaced = placed
                bestGrid = grid
            }

            if (bestPlaced.size >= sortedWords.size) {
                break
            }
        }

        // Fill remaining spaces with weighted random letters
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (bestGrid[r][c] == ' ') {
                    bestGrid[r][c] = LETTER_POOL[random.nextInt(LETTER_POOL.length)]
                }
            }
        }

        val immutableGrid = bestGrid.map { it.toList() }

        return GeneratedBoard(
            size = size,
            grid = immutableGrid,
            placedWords = bestPlaced
        )
    }

    private fun tryPlaceWord(
        grid: Array<CharArray>,
        size: Int,
        word: String,
        allowedDirections: List<Direction>,
        random: Random
    ): Placement? {
        val len = word.length
        val directions = allowedDirections.shuffled(random)
        val rows = (0 until size).shuffled(random)
        val cols = (0 until size).shuffled(random)

        for (dir in directions) {
            for (r in rows) {
                for (c in cols) {
                    val endR = r + dir.dRow * (len - 1)
                    val endC = c + dir.dCol * (len - 1)

                    if (endR in 0 until size && endC in 0 until size) {
                        var canPlace = true
                        for (i in 0 until len) {
                            val checkR = r + dir.dRow * i
                            val checkC = c + dir.dCol * i
                            val current = grid[checkR][checkC]
                            if (current != ' ' && current != word[i]) {
                                canPlace = false
                                break
                            }
                        }

                        if (canPlace) {
                            return Placement(r, c, endR, endC)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun commitWord(
        grid: Array<CharArray>,
        word: String,
        startRow: Int,
        startCol: Int,
        endRow: Int,
        endCol: Int
    ) {
        val len = word.length
        val dRow = if (len > 1) (endRow - startRow) / (len - 1) else 0
        val dCol = if (len > 1) (endCol - startCol) / (len - 1) else 0

        for (i in 0 until len) {
            val r = startRow + dRow * i
            val c = startCol + dCol * i
            grid[r][c] = word[i]
        }
    }

    private data class Placement(
        val startRow: Int,
        val startCol: Int,
        val endRow: Int,
        val endCol: Int
    )
}
