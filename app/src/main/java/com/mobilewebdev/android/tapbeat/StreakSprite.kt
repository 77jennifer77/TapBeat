package com.mobilewebdev.android.tapbeat

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log

class StreakSprite(private var x: Float, private var y: Float, private val streak: Int): Sprite, Updatable {
    private val paint: Paint = Paint()
    private var phase = 255
    private var dy = 0
    private var fade = false
    private var numSteps = 255
    private var drawing = true


    override fun draw(canvas: Canvas) {
        if(drawing) {
            paint.color = Color.argb(phase, 255, 0, 0)
            paint.textSize = 70f
            canvas.drawText("STREAK!!", x, y, paint)
        }
    }

    override fun update() {
        phase--
        y -= 2.5f
        if(phase <= 0) {
            phase = 255
            drawing = false
        }
    }

    override fun offScreen(): Boolean {
        return false
    }

    override fun clicked(): Boolean {
        return false
    }

    override fun reset() {}
}