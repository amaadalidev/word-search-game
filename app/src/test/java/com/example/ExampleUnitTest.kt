package com.example

import com.example.engine.WordDataBank
import com.example.engine.WordSearchGenerator
import com.example.model.Difficulty
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testWordDataBankCategories() {
    assertEquals(17, WordDataBank.categories.size)
    val animals = WordDataBank.getWordsForTheme("animals")
    assertTrue(animals.isNotEmpty())
    assertTrue(animals.contains("LION"))
  }

  @Test
  fun testWordSearchGeneratorEasy() {
    val words = listOf("CAT", "DOG", "FOX")
    val board = WordSearchGenerator.generate(Difficulty.EASY, words)
    assertEquals(6, board.size)
    assertEquals(6, board.grid.size)
    assertEquals(6, board.grid[0].size)
    assertTrue(board.placedWords.isNotEmpty())
  }

  @Test
  fun testWordSearchGeneratorPro() {
    val words = listOf("GIRAFFE", "ELEPHANT", "MONKEY", "PENGUIN")
    val board = WordSearchGenerator.generate(Difficulty.PRO, words)
    assertEquals(9, board.size)
    assertTrue(board.placedWords.isNotEmpty())
  }
}
