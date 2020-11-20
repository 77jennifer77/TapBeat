package com.mobilewebdev.android.tapbeat

import android.content.res.Resources
import android.graphics.*
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class GameViewModel: ViewModel() {
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()
    private val paint: Paint = Paint()
    private val targetCirclePaint_purple = Paint()
    private val targetCirclePaint_gold = Paint()
    private val targetCirclePaint_blue = Paint()
    private var score = 0
    private lateinit var iconImage: Bitmap
    private lateinit var note: Bitmap
    private lateinit var blank: Bitmap


    //val maxNotes = 7
    private val sprites = mutableListOf<Sprite>()
    private val actionItems = mutableListOf<ActionItem>()
    private val updatables = mutableListOf<Updatable>()

    private var loaded = false
    fun load(resources: Resources?) {
        if(!loaded) {

            // BACKGROUND GRAPHIC DRAWN HERE
            sprites.add(VectorSprite(this))

            blank = BitmapFactory.decodeResource(resources, R.drawable.blank)

            iconImage = BitmapFactory.decodeResource(resources, R.drawable.purplenote)
            note = BitmapFactory.decodeResource(resources, R.drawable.purplenote)
            for(x in 0..20) {
                var characterSprite = CharacterSprite(note, 5.0f, 400*x+(300..500).random())
                sprites.add(characterSprite)
                updatables.add(characterSprite)
                actionItems.add(characterSprite)
            }

            note = BitmapFactory.decodeResource(resources, R.drawable.bluenote)
            for(x in 0..20) {
                var characterSprite = CharacterSprite2(note, 5.0f, 700*x+(200..400).random())
                sprites.add(characterSprite)
                updatables.add(characterSprite)
                actionItems.add(characterSprite)
            }

            note = BitmapFactory.decodeResource(resources, R.drawable.goldnote)
            for(x in 0..20) {
                var characterSprite = CharacterSprite3(note, 5.0f, 500*x+(200..400).random(), blank)
                sprites.add(characterSprite)
                updatables.add(characterSprite)
                actionItems.add(characterSprite)
            }

            loaded = true
        }
    }

    fun doClick(x: Int, y: Int): Boolean {
        var any = false
        for (item in actionItems) {
            if (item.doClick(x, y)) {
                any = true
                score += 100
            }
        }
        return any
    }

    fun draw(canvas: Canvas) {
        for (sprite in sprites) {
            sprite.draw(canvas)
        }

        paint.color = Color.rgb(255,255,255)
        paint.textSize = 70f
        targetCirclePaint_purple.color = Color.rgb(201, 82, 255)
        targetCirclePaint_blue.color = Color.rgb(0, 172, 255)
        targetCirclePaint_gold.color = Color.rgb(244, 186, 52)


        targetCirclePaint_purple.setAntiAlias(true);
        targetCirclePaint_purple.setStrokeWidth(iconImage.width.toFloat()/25);
        targetCirclePaint_purple.setStyle(Paint.Style.STROKE);
        targetCirclePaint_purple.setStrokeJoin(Paint.Join.ROUND);
        targetCirclePaint_purple.setStrokeCap(Paint.Cap.ROUND);

        targetCirclePaint_blue.setAntiAlias(true);
        targetCirclePaint_blue.setStrokeWidth(iconImage.width.toFloat()/25);
        targetCirclePaint_blue.setStyle(Paint.Style.STROKE);
        targetCirclePaint_blue.setStrokeJoin(Paint.Join.ROUND);
        targetCirclePaint_blue.setStrokeCap(Paint.Cap.ROUND);

        targetCirclePaint_gold.setAntiAlias(true);
        targetCirclePaint_gold.setStrokeWidth(iconImage.width.toFloat()/25);
        targetCirclePaint_gold.setStyle(Paint.Style.STROKE);
        targetCirclePaint_gold.setStrokeJoin(Paint.Join.ROUND);
        targetCirclePaint_gold.setStrokeCap(Paint.Cap.ROUND);

        /*
        canvas.drawCircle( screenWidth * 0.175f,screenHeight*0.95f,125f, paint)
        canvas.drawCircle( screenWidth * 0.475f ,screenHeight*0.95f,125f, paint)
        canvas.drawCircle( screenWidth * 0.775f ,screenHeight*0.95f,125f, paint)
         */
        canvas.drawText("Score: $score", 10f, 75f, paint)

        canvas.drawCircle( screenWidth * 0.175f,screenHeight*0.93f, iconImage.width.toFloat()/4, targetCirclePaint_purple)
        canvas.drawCircle( screenWidth * 0.475f ,screenHeight*0.93f,iconImage.width.toFloat()/4, targetCirclePaint_blue)
        canvas.drawCircle( screenWidth * 0.775f ,screenHeight*0.93f,iconImage.width.toFloat()/4, targetCirclePaint_gold)
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

}
