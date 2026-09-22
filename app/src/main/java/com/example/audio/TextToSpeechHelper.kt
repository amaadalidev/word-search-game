package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = try {
        TextToSpeech(context.applicationContext, this)
    } catch (e: Exception) {
        Log.w("TextToSpeechHelper", "TTS service not available on this device", e)
        null
    }
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to default system locale
                tts?.language = Locale.getDefault()
            }
            tts?.setPitch(1.05f)
            tts?.setSpeechRate(0.95f)
            isInitialized = true
        } else {
            Log.e("TextToSpeechHelper", "TTS Initialization failed with status: $status")
        }
    }

    fun speak(text: String) {
        if (!isInitialized || tts == null) return
        try {
            // Pronounce cleanly in titlecase/lowercase
            val speakableText = text.trim().lowercase(Locale.US)
            tts?.speak(speakableText, TextToSpeech.QUEUE_FLUSH, null, "WordUtterance_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.e("TextToSpeechHelper", "Error speaking text: $text", e)
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
            isInitialized = false
        } catch (_: Exception) {}
    }
}
