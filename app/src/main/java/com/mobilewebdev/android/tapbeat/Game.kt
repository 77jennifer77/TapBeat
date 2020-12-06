package com.mobilewebdev.android.tapbeat

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class Game : ComponentActivity() {
    private lateinit var player: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.load(resources)
//        requestWindowFeature(setContentView(GameView(this).attachViewModel(gameViewModel)))
//        startActivity(Intent(this, GameViewModel::class.java))
        setContentView(GameView(this).attachViewModel(gameViewModel))

        player = MediaPlayer.create(this, R.raw.song)
        player.setLooping(false);
        player.start();
    }
}
