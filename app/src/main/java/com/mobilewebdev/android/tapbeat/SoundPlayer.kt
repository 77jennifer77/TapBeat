package com.mobilewebdev.android.tapbeat

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

class SoundPlayer(context: Context) {

    // For sound FX
    private val soundPool: SoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)

    companion object {
        var hitNoteId = -1
        var missNoteId = -1
    }

    init {
        try {
            // Create objects of the 2 required classes
            val assetManager = context.assets
            var descriptor: AssetFileDescriptor

            // Load our fx in memory ready for use
            descriptor = assetManager.openFd("hit.mp3")
            hitNoteId = soundPool.load(descriptor, 0)

            descriptor = assetManager.openFd("miss.mp3")
            missNoteId = soundPool.load(descriptor, 0)

        } catch (e: IOException) {
            // Print an error message to the console
            Log.e("TAG", "failed to load sound files")
        }
    }

    fun playSound(id: Int){
        soundPool.play(id, 1f, 1f, 0, 0, 1f)
    }
}