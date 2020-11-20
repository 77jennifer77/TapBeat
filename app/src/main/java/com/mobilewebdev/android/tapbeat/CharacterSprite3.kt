package com.mobilewebdev.android.tapbeat
import android.content.res.Resources
import android.graphics.*
import android.util.Log

class CharacterSprite3(private var image: Bitmap, dy0: Float, y: Int, private var blank: Bitmap): Sprite, Updatable, ActionItem {
    private var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private var playerX = screenWidth * 0.6f
    private var playerY = screenHeight * 0.01f - y
    private var yVelocity = 7.5f
    private var clicked = false
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
        playerY += yVelocity
        position.top += yVelocity
        position.bottom += yVelocity
        if(playerY > screenHeight - image.height) {
            yVelocity = yVelocity
        }
    }

    override fun doClick(px: Int, py: Int): Boolean {
        if(position.left < px && position.right > px && !clicked) {
            if(position.bottom > py && py > position.top) {
                Log.d("TAG", "GOLD")
                clicked = true
                // change image to a successful note click
                image = blank
                return true
            }
        }
        return false
    }


}