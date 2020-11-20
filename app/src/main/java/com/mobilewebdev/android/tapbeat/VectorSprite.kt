package com.mobilewebdev.android.tapbeat

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class VectorSprite(private val gameViewModel: GameViewModel): Sprite {
    private val paint1 = Paint()
    private val paint2 = Paint()
    private val paint3 = Paint()

    init {
        paint1.color = Color.rgb(200,0, 0)
        paint2.color = Color.rgb(0,200, 0)
        paint3.color = Color.rgb(0,0, 200)
    }

    override fun draw(canvas: Canvas) {
        val x1 = gameViewModel.x1.value
        val y1 = gameViewModel.y1.value
        val x2 = gameViewModel.x2.value
        val y2 = gameViewModel.y2.value
        val x3 = gameViewModel.x3.value
        val y3 = gameViewModel.y3.value
        drawWeb(canvas, paint3, x3,y3,x1,y1,x2,y2)
        drawWeb(canvas, paint2, x2,y2,x3,y3,x1,y1)
        drawWeb(canvas, paint1, x1,y1,x2,y2,x3,y3)
    }

    private fun drawWeb(canvas: Canvas, paint: Paint, x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float) {
                for(t in 0 until 1000 step 125) {
                    val t1 = t.toFloat() / 1000.0f
                    val t2 = 1.0f - t1
                    val ax = x1 * t1 + x2 * t2
                    val ay = y1 * t1 + y2 * t2
                    val bx = x2 * t1 + x3 * t2
                    val by = y2 * t1 + y3 * t2
                    canvas.drawLine(ax, ay, bx, by, paint)
        }
    }
}