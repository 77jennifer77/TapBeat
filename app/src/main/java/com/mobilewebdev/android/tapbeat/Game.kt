package com.mobilewebdev.android.tapbeat

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class Game : ComponentActivity() {
    private lateinit var player: MediaPlayer
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.load(resources)
        setContentView(GameView(this).attachViewModel(gameViewModel))

        player = MediaPlayer.create(this, R.raw.song)
        player.setLooping(false);
        player.start();
    }

    override fun onBackPressed() {
        super.onBackPressed()
        player.stop()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onResume() {
        super.onResume()
        player.start()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}
