package com.mobilewebdev.android.tapbeat

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Picture
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context): SurfaceView(context), SurfaceHolder.Callback {
    private val gameThread: GameThread
    private lateinit var gameViewModel: GameViewModel
    private val soundPlayer = SoundPlayer(context)
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()

    init {
        holder.addCallback(this)
        gameThread = GameThread(holder, this)
        isFocusable = true
    }

    fun attachViewModel(model: GameViewModel): GameView {
        gameViewModel = model
        gameThread.attachViewModel(model)
        return this
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread.setRunning(true)
        gameThread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while(retry) {
            try {
                gameThread.setRunning(false)
                gameThread.join()
            }
            catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var any = false
        if(event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            any = gameThread.doClick(x, y)
            if(any == true){
                soundPlayer.playSound(SoundPlayer.hitNoteId)
            }
            else {
                soundPlayer.playSound(SoundPlayer.missNoteId)
            }
        }
        return any
    }

    override fun draw(canvas: Canvas) {
        var backing = BitmapFactory.decodeResource(resources, R.drawable.background)
        super.draw(canvas)
        canvas.drawColor(Color.BLACK)
        canvas.drawBitmap(backing, 0f, screenHeight/5, null)
        gameViewModel.draw(canvas)

    }
}
