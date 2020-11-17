package ryan.d.gametut

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
    }

    init {
        try {
            // Create objects of the 2 required classes
            val assetManager = context.assets
            var descriptor: AssetFileDescriptor

            // Load our fx in memory ready for use
            descriptor = assetManager.openFd("hitNote.ogg")
            hitNoteId = soundPool.load(descriptor, 0)

        } catch (e: IOException) {
            // Print an error message to the console
            Log.e("TAG", "failed to load sound files")
        }
    }

    fun playSound(id: Int){
        soundPool.play(id, 1f, 1f, 0, 0, 1f)
    }
}