package com.mobilewebdev.android.tapbeat

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.media.MediaPlayer
import android.util.Log

class CharacterSprite(private val image: Bitmap, dx0: Float, dy0: Float): Sprite, Updatable, ActionItem {
    private var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private var playerX = screenWidth * 0f
    private var playerY = screenHeight * 0.01f
    //private var xVelocity = 0f
    private var yVelocity = 7.5f
    var position = RectF(
        playerX+80,
        playerY+80,
        playerX+image.width-80,
        playerY+image.height-80
    )

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, playerX, playerY, null)
    }

    override fun getY(): Float {
        return playerY
    }

    override fun update() {
        //playerX += xVelocity
        playerY += yVelocity
        //position.left += xVelocity
        //position.right += xVelocity
        position.top += yVelocity
        position.bottom += yVelocity
        if(playerX > screenWidth - image.width || playerX < 0) {
            //xVelocity = -xVelocity
        }
        if(playerY > screenHeight - image.height) {
            yVelocity = yVelocity
        }
    }

    override fun doClick(px: Int, py: Int): Boolean {
        if(position.left < px && position.right > px) {
            if(position.bottom > py && py > position.top) {
                Log.d("TAG", "RETURNING TRUE")
                return true
            }
        }
        return false
    }
}