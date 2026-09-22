package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

class SoundManager private constructor(context: Context) {

    private val appContext = context.applicationContext
    private val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as? AudioManager

    private val sampleRate = 44100

    private val clickTracks = mutableListOf<AudioTrack>()
    private var clickTrackIndex = 0

    private val tileTracks = mutableListOf<AudioTrack>()
    private var wordMatchTrack: AudioTrack? = null
    private var sparkleTrack: AudioTrack? = null
    private var levelWinTrack: AudioTrack? = null

    private val allTracks = mutableListOf<AudioTrack>()

    init {
        initializeAudioTracks()
    }

    private fun initializeAudioTracks() {
        try {
            // Dual tracks for clicks so rapid successive clicks play smoothly
            val clickSamples = generateClickSamples(sampleRate)
            createStaticTrack(clickSamples, sampleRate)?.let {
                clickTracks.add(it)
                allTracks.add(it)
            }
            createStaticTrack(clickSamples, sampleRate)?.let {
                clickTracks.add(it)
                allTracks.add(it)
            }

            // Ascending musical chime frequencies for tile dragging
            // D5, E5, F#5, G5, A5, B5, C#6, D6
            val tileFrequencies = doubleArrayOf(
                587.33, 659.25, 739.99, 783.99,
                880.00, 987.77, 1108.73, 1174.66
            )
            for (freq in tileFrequencies) {
                val samples = generateTileSelectSamples(freq, sampleRate)
                createStaticTrack(samples, sampleRate)?.let {
                    tileTracks.add(it)
                    allTracks.add(it)
                }
            }

            // Word match fanfare
            val matchSamples = generateWordMatchSamples(sampleRate)
            createStaticTrack(matchSamples, sampleRate)?.let {
                wordMatchTrack = it
                allTracks.add(it)
            }

            // Sparkle sound
            val sparkleSamples = generateSparkleSamples(sampleRate)
            createStaticTrack(sparkleSamples, sampleRate)?.let {
                sparkleTrack = it
                allTracks.add(it)
            }

            // Level win fanfare
            val winSamples = generateLevelWinSamples(sampleRate)
            createStaticTrack(winSamples, sampleRate)?.let {
                levelWinTrack = it
                allTracks.add(it)
            }
        } catch (e: Exception) {
            Log.e("SoundManager", "Error initializing audio tracks", e)
        }
    }

    private fun createStaticTrack(samples: ShortArray, rate: Int): AudioTrack? {
        return try {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val format = AudioFormat.Builder()
                .setSampleRate(rate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build()

            val bufferSize = samples.size * 2
            val track = AudioTrack.Builder()
                .setAudioAttributes(attributes)
                .setAudioFormat(format)
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(samples, 0, samples.size)
            track
        } catch (e: Exception) {
            Log.w("SoundManager", "Could not build AudioTrack", e)
            null
        }
    }

    fun playClick() {
        if (clickTracks.isNotEmpty()) {
            val track = clickTracks[clickTrackIndex % clickTracks.size]
            clickTrackIndex++
            playTrack(track, 0.9f)
        } else {
            try {
                audioManager?.playSoundEffect(AudioManager.FX_KEY_CLICK, 0.6f)
            } catch (_: Exception) {}
        }
    }

    fun playTileSelect(step: Int = 0) {
        if (tileTracks.isEmpty()) return
        val index = step.coerceIn(0, tileTracks.lastIndex)
        playTrack(tileTracks[index], 0.85f)
    }

    fun playWordMatch() {
        playTrack(wordMatchTrack, 1.0f)
    }

    fun playSparkle() {
        playTrack(sparkleTrack, 0.95f)
    }

    fun playLevelComplete() {
        playTrack(levelWinTrack, 1.0f)
    }

    private fun playTrack(track: AudioTrack?, volume: Float) {
        if (track == null) return
        try {
            if (track.state == AudioTrack.STATE_INITIALIZED) {
                track.stop()
                track.reloadStaticData()
                track.setVolume(volume)
                track.play()
            }
        } catch (e: Exception) {
            Log.w("SoundManager", "Audio playback issue: ${e.message}")
        }
    }

    fun release() {
        for (track in allTracks) {
            try {
                track.stop()
                track.release()
            } catch (_: Exception) {}
        }
        allTracks.clear()
        clickTracks.clear()
        tileTracks.clear()
        wordMatchTrack = null
        sparkleTrack = null
        levelWinTrack = null
    }

    // --- Sound Synthesis Engines (44.1 kHz, 16-bit Mono PCM) ---

    private fun generateClickSamples(rate: Int): ShortArray {
        val durationMs = 30
        val numSamples = (rate * (durationMs / 1000.0)).toInt()
        val samples = ShortArray(numSamples)
        var phase = 0.0

        for (i in 0 until numSamples) {
            val t = i.toDouble() / rate
            val progress = i.toDouble() / numSamples
            val freq = 850.0 - 550.0 * progress
            phase += 2.0 * PI * freq / rate
            val env = exp(-t * 110.0)
            val value = (sin(phase) * env * 24000.0).toInt().coerceIn(-32767, 32767)
            samples[i] = value.toShort()
        }
        return samples
    }

    private fun generateTileSelectSamples(f0: Double, rate: Int): ShortArray {
        val durationMs = 60
        val numSamples = (rate * (durationMs / 1000.0)).toInt()
        val samples = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / rate
            val env = if (t < 0.004) {
                t / 0.004
            } else {
                exp(-(t - 0.004) * 55.0)
            }
            val wave = 0.8 * sin(2.0 * PI * f0 * t) + 0.2 * sin(2.0 * PI * f0 * 2.0 * t)
            val value = (wave * env * 24000.0).toInt().coerceIn(-32767, 32767)
            samples[i] = value.toShort()
        }
        return samples
    }

    private fun generateWordMatchSamples(rate: Int): ShortArray {
        val durationMs = 380
        val numSamples = (rate * (durationMs / 1000.0)).toInt()
        val samples = ShortArray(numSamples)

        val notes = listOf(
            Triple(0.00, 523.25, 0.20),
            Triple(0.07, 659.25, 0.20),
            Triple(0.14, 783.99, 0.20),
            Triple(0.21, 1046.50, 0.30)
        )

        for (i in 0 until numSamples) {
            val t = i.toDouble() / rate
            var sum = 0.0

            for ((startT, freq, duration) in notes) {
                if (t >= startT) {
                    val noteT = t - startT
                    if (noteT < duration) {
                        val env = exp(-noteT * 14.0)
                        sum += (0.8 * sin(2.0 * PI * freq * noteT) + 0.2 * sin(2.0 * PI * freq * 2.0 * noteT)) * env
                    }
                }
            }

            val value = (sum * 14000.0).toInt().coerceIn(-32767, 32767)
            samples[i] = value.toShort()
        }
        return samples
    }

    private fun generateSparkleSamples(rate: Int): ShortArray {
        val durationMs = 300
        val numSamples = (rate * (durationMs / 1000.0)).toInt()
        val samples = ShortArray(numSamples)

        val tones = listOf(880.0, 1108.73, 1318.51, 1661.22, 2093.00)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / rate
            var sum = 0.0

            for (idx in tones.indices) {
                val startT = idx * 0.04
                if (t >= startT) {
                    val noteT = t - startT
                    val env = exp(-noteT * 24.0)
                    sum += sin(2.0 * PI * tones[idx] * noteT) * env * 0.4
                }
            }

            val value = (sum * 18000.0).toInt().coerceIn(-32767, 32767)
            samples[i] = value.toShort()
        }
        return samples
    }

    private fun generateLevelWinSamples(rate: Int): ShortArray {
        val durationMs = 600
        val numSamples = (rate * (durationMs / 1000.0)).toInt()
        val samples = ShortArray(numSamples)

        val notes = listOf(
            Triple(0.00, 392.00, 0.12),
            Triple(0.10, 523.25, 0.12),
            Triple(0.20, 659.25, 0.12),
            Triple(0.30, 783.99, 0.14),
            Triple(0.38, 523.25, 0.40),
            Triple(0.38, 659.25, 0.40),
            Triple(0.38, 783.99, 0.40),
            Triple(0.38, 1046.50, 0.40)
        )

        for (i in 0 until numSamples) {
            val t = i.toDouble() / rate
            var sum = 0.0

            for ((startT, freq, duration) in notes) {
                if (t >= startT) {
                    val noteT = t - startT
                    if (noteT < duration) {
                        val env = exp(-noteT * 7.5)
                        sum += sin(2.0 * PI * freq * noteT) * env * 0.3
                    }
                }
            }

            val value = (sum * 16000.0).toInt().coerceIn(-32767, 32767)
            samples[i] = value.toShort()
        }
        return samples
    }

    companion object {
        @Volatile
        private var instance: SoundManager? = null

        fun getInstance(context: Context): SoundManager {
            return instance ?: synchronized(this) {
                instance ?: SoundManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
