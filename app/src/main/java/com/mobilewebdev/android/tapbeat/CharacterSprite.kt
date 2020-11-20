package com.mobilewebdev.android.tapbeat
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.media.MediaPlayer
import android.util.Log

class CharacterSprite(private val image: Bitmap, dy0: Float, private val y: Int, private val invisible: Bitmap): Sprite, Updatable, ActionItem {
    private var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private var playerX = screenWidth * 0f
    private var playerY = screenHeight * 0.01f - y
    private var yVelocity = 7.5f
    private var clicked = false
    private var note = image
    private var position = RectF(
        playerX+80,
        playerY+80,
        playerX+image.width-80,
        playerY+image.height-80
    )

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(note, playerX, playerY, null)
    }

    override fun clicked(): Boolean {
        return clicked
    }

    override fun update() {
        playerY += yVelocity
        position.top += yVelocity
        position.bottom += yVelocity
        if(playerY > screenHeight - image.height) {
            yVelocity = yVelocity
        }
    }

    override fun offScreen(): Boolean {
        if(playerY > screenHeight*1.1) {
            return true
        }
        return false
    }

    override fun reset() {
        playerY = screenHeight * 0.01f - y
        position.top = playerY+80
        position.bottom = playerY+image.height-80
        note = image
        clicked = false
    }

    override fun doClick(px: Int, py: Int): Boolean {
        if(position.left < px && position.right > px && !clicked) {
            if(position.bottom > py && py > position.top) {
                Log.d("TAG", "PURPLE")
                note = invisible
                clicked = true
                return true
            }
        }
        return false
    }
}