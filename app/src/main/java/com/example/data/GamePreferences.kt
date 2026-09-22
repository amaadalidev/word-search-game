package com.example.data

import android.content.Context
import android.content.SharedPreferences

class GamePreferences(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("word_search_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_COINS = "user_coins"
        private const val KEY_UNLOCKED_THEMES = "unlocked_themes"
        private const val KEY_VIBRATE = "vibrate_enabled"
        private const val KEY_SOUND = "sound_enabled"
        private const val KEY_CURRENT_LEVEL_PREFIX = "level_progress_"
    }

    var coins: Int
        get() = prefs.getInt(KEY_COINS, 300)
        set(value) = prefs.edit().putInt(KEY_COINS, value).apply()

    var vibrateEnabled: Boolean
        get() = prefs.getBoolean(KEY_VIBRATE, true)
        set(value) = prefs.edit().putBoolean(KEY_VIBRATE, value).apply()

    var soundEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND, true)
        set(value) = prefs.edit().putBoolean(KEY_SOUND, value).apply()

    fun getUnlockedThemes(): Set<String> {
        val defaultThemes = setOf("animals", "colors", "cities", "nature", "house", "adjectives", "space")
        return prefs.getStringSet(KEY_UNLOCKED_THEMES, defaultThemes) ?: defaultThemes
    }

    fun isThemeUnlocked(themeId: String): Boolean {
        return getUnlockedThemes().contains(themeId)
    }

    fun unlockTheme(themeId: String) {
        val current = getUnlockedThemes().toMutableSet()
        current.add(themeId)
        prefs.edit().putStringSet(KEY_UNLOCKED_THEMES, current).apply()
    }

    fun getLevelProgress(themeId: String, difficulty: String): Int {
        return prefs.getInt("$KEY_CURRENT_LEVEL_PREFIX${themeId}_$difficulty", 0)
    }

    fun setLevelProgress(themeId: String, difficulty: String, level: Int) {
        prefs.edit().putInt("$KEY_CURRENT_LEVEL_PREFIX${themeId}_$difficulty", level).apply()
    }

    fun addCoins(amount: Int) {
        coins += amount
    }

    fun spendCoins(amount: Int): Boolean {
        if (coins >= amount) {
            coins -= amount
            return true
        }
        return false
    }
}
