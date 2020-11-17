package ryan.d.gametut

import android.graphics.Bitmap

class TiledBitmap(private val image: Bitmap, private val numCol: Int, numRows: Int) {
    private val bitmapW: Int = image.width
    private val bitmapH: Int = image.height
    val iconW = bitmapW / numCol
    val iconH = bitmapH / numRows
    private val subImages = Array<Bitmap?>(numCol * numRows){null}

    fun getImage(col: Int, row: Int): Bitmap {
        val index = col + numCol * row
        return subImages[index] ?: makeImage(col, row)
    }

    fun getImageByIndex(index: Int): Bitmap {
        val col = index % numCol
        val row = index / numCol
        return subImages[index] ?: makeImage(col, row)
    }

    private fun makeImage(col: Int, row: Int): Bitmap {
        val index = col + numCol * row
        val subImage = Bitmap.createBitmap(image, col * iconW, row * iconH, iconW, iconH)
        subImages[index] = subImage
        return subImage
    }

}