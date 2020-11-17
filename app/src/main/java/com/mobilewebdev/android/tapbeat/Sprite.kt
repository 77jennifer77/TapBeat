package ryan.d.gametut

import android.graphics.Canvas

interface Sprite {
    fun draw(canvas: Canvas)
    fun getY(): Float
}