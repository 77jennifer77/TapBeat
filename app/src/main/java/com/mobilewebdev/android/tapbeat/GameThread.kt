package com.mobilewebdev.android.tapbeat

import android.content.res.Resources
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import java.lang.Exception
import kotlin.random.Random

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView): Thread() {
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()
    private var running = false
    private var targetFPS = 60L
    private lateinit var gameViewModel: GameViewModel

    fun attachViewModel(model: GameViewModel) {
        gameViewModel = model
    }

    private var tick = Random.nextInt(0,200)
    override fun run() {
        var startTime = 0L
        val targetTime = 1000L / targetFPS

        while (running) {
            var canvas: Canvas? = null

            try {
                startTime = System.nanoTime()
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    updateGame()
                    gameView.draw(canvas)
                }
            } catch (e: Exception) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            val timeMillis = (System.nanoTime() - startTime) / 1000000L
            val waitTime = targetTime - timeMillis

            if(waitTime > 0L) {
                try {
                    sleep(waitTime)
                } catch (e: Exception) {}
            }
        }
    }

    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }

    fun doClick(x: Int, y: Int): Boolean {
        /* Check that User Click is in note click zone*/
        if (y < screenHeight && y > screenHeight*0.9) {
            Log.d("TAG", "CLICKED (" + x + "," + y + ")")
            return gameViewModel.doClick(x, y)
        }
        else{
            return false
        }
    }

    fun updateGame() {
        gameViewModel.update()
    }
}