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

class MainActivity : ComponentActivity() {
    private lateinit var playButton: Button
    private lateinit var player: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.play_button)

        playButton.setOnClickListener { view: View ->
            startGame()
        }
    }
    private fun startGame(){
        val intent = Intent(this, Game::class.java)
        return startActivity(intent)
    }
}
