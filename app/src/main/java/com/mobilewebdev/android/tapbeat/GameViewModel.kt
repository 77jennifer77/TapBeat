package ryan.d.gametut

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class GameViewModel: ViewModel() {
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()

    //val maxNotes = 7
    private val sprites = mutableListOf<Sprite>()
    private val actionItems = mutableListOf<ActionItem>()
    private val updatables = mutableListOf<Updatable>()

    private var loaded = false

    fun load(resources: Resources?) {
        if(!loaded) {

            // Triangle Graphic Background
            sprites.add(VectorSprite(this))

            loaded = true
        }
    }

    fun doClick(x: Int, y: Int): Boolean {
        var any = false
        for (item in actionItems) {
            if (item.doClick(x, y)) {
                any = true
            }
        }
        return any
    }

    fun draw(canvas: Canvas) {
        for (sprite in sprites) {
            sprite.draw(canvas)
        }
    }

    fun update() {
        x1.step()
        y1.step()
        x2.step()
        y2.step()
        x3.step()
        y3.step()
        for(updatable in updatables) {
            updatable.update()
        }
    }

    fun checkNotes() {
        for(note in sprites) {
            if(note.getY() > screenHeight) {
                sprites.remove(note)
                actionItems.remove(note as ActionItem)
                updatables.remove(note as Updatable)
            }
        }
    }

    val x1 = Coordinate(screenWidth)
    val y1 = Coordinate(screenHeight)
    val x2 = Coordinate(screenWidth)
    val y2 = Coordinate(screenHeight)
    val x3 = Coordinate(screenWidth)
    val y3 = Coordinate(screenHeight)

    class Coordinate(private val limit: Float) {
        var value = limit / 2.0f
        private var delta = 0.0f

        fun step() {
            value += delta
            if(value < 0.0f || value > limit) {
                delta = -delta
            }
            delta += Math.random().toFloat() * 4.0f - 2.0f
            if(delta < -20.0f) {
                delta = -20.0f
            }
            if(delta > 20.0f) {
                delta = 20.0f
            }
        }
    }

    fun newPurpleNote(resources: Resources?) {
            val image = BitmapFactory.decodeResource(resources, R.drawable.purplenote)
            var characterSprite = CharacterSprite(image, 1.0f, 5.0f)
            sprites.add(characterSprite)
            updatables.add(characterSprite)
            actionItems.add(characterSprite)
    }
    fun newBlueNote(resources: Resources?) {
            val image2 = BitmapFactory.decodeResource(resources, R.drawable.bluenote)
            var characterSprite2 = CharacterSprite2(image2, 1.0f, 5.0f)
            sprites.add(characterSprite2)
            updatables.add(characterSprite2)
            actionItems.add(characterSprite2)
    }
    fun newGoldNote(resources: Resources?) {
            val image3 = BitmapFactory.decodeResource(resources, R.drawable.goldnote)
            var characterSprite3 = CharacterSprite3(image3, 1.0f, 5.0f)
            sprites.add(characterSprite3)
            updatables.add(characterSprite3)
            actionItems.add(characterSprite3)
    }
}
