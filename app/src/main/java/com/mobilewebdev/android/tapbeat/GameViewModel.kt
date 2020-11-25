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
    private var streak = 0
    private lateinit var note: Bitmap
    private var radius = 0f


    private val sprites = mutableListOf<Sprite>()
    private val actionItems = mutableListOf<ActionItem>()
    private val updatables = mutableListOf<Updatable>()

    private var loaded = false
    fun load(resources: Resources?) {
        if(!loaded) {
            // Background Visual
            sprites.add(VectorSprite(this))

            // Creating Notes
            note = BitmapFactory.decodeResource(resources, R.drawable.purplenote)
            for(x in 1..3) {
                var characterSprite = CharacterSprite(note, 5.0f, 1200*x)
                sprites.add(characterSprite)
                updatables.add(characterSprite)
                actionItems.add(characterSprite)
            }
            note = BitmapFactory.decodeResource(resources, R.drawable.bluenote)
            for(x in 1..3) {
                var characterSprite = CharacterSprite2(note, 5.0f, 600*x)
                sprites.add(characterSprite)
                updatables.add(characterSprite)
                actionItems.add(characterSprite)
            }
            note = BitmapFactory.decodeResource(resources, R.drawable.goldnote)
            for(x in 1..3) {
                var characterSprite = CharacterSprite3(note, 5.0f, 800*x)
                sprites.add(characterSprite)
                updatables.add(characterSprite)
                actionItems.add(characterSprite)
            }

            radius = note.width.toFloat()/4
            loaded = true
        }
    }

    fun doClick(x: Int, y: Int): Boolean {
        var any = false
        for (item in actionItems) {
            if (item.doClick(x, y)) {
                any = true
                streak += 1
                score += 100
            }
        }
        // TODO: INITIALIZE STREAK SPRITE IN LOAD AND DRAW THE SINGULAR SPRITE WHEN NECESSARY
        if(streak % 15 == 0) {
            val streaking = StreakSprite(screenWidth / 3, screenHeight / 3, streak)
            sprites.add(streaking)
            updatables.add(streaking)
            Log.d("TAG", "sprites: ${sprites.size}")
        }
        return any
    }

    fun draw(canvas: Canvas) {
        paint.color = Color.rgb(255,255,255)
        paint.textSize = 75f
        targetCirclePaint_purple.color = Color.rgb(201, 82, 255)
        targetCirclePaint_blue.color = Color.rgb(0, 172, 255)
        targetCirclePaint_gold.color = Color.rgb(244, 186, 52)


        targetCirclePaint_purple.setAntiAlias(true);
        targetCirclePaint_purple.setStrokeWidth(note.width.toFloat()/35);
        targetCirclePaint_purple.setStyle(Paint.Style.STROKE);
        targetCirclePaint_purple.setStrokeJoin(Paint.Join.ROUND);
        targetCirclePaint_purple.setStrokeCap(Paint.Cap.ROUND);

        targetCirclePaint_blue.setAntiAlias(true);
        targetCirclePaint_blue.setStrokeWidth(note.width.toFloat()/35);
        targetCirclePaint_blue.setStyle(Paint.Style.STROKE);
        targetCirclePaint_blue.setStrokeJoin(Paint.Join.ROUND);
        targetCirclePaint_blue.setStrokeCap(Paint.Cap.ROUND);

        targetCirclePaint_gold.setAntiAlias(true);
        targetCirclePaint_gold.setStrokeWidth(note.width.toFloat()/35);
        targetCirclePaint_gold.setStyle(Paint.Style.STROKE);
        targetCirclePaint_gold.setStrokeJoin(Paint.Join.ROUND);
        targetCirclePaint_gold.setStrokeCap(Paint.Cap.ROUND);

        /*
        canvas.drawCircle( screenWidth * 0.175f,screenHeight*0.95f,125f, paint)
        canvas.drawCircle( screenWidth * 0.475f ,screenHeight*0.95f,125f, paint)
        canvas.drawCircle( screenWidth * 0.775f ,screenHeight*0.95f,125f, paint)
         */

        for (sprite in sprites) {
            sprite.draw(canvas)
        }
        canvas.drawText("Score: $score", 6*screenWidth/10, 75f, paint)
        canvas.drawText("Streak: $streak", screenWidth/10, 75f, paint)
        canvas.drawCircle( screenWidth * 0.175f,screenHeight*0.93f, radius, targetCirclePaint_purple)
        canvas.drawCircle( screenWidth * 0.475f ,screenHeight*0.93f,radius, targetCirclePaint_blue)
        canvas.drawCircle( screenWidth * 0.775f ,screenHeight*0.93f,radius, targetCirclePaint_gold)
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
            if(updatable.offScreen()) {
                if (!updatable.clicked()){
                    streak = 0
                }
                updatable.reset()
            }
        }
    }




    // Logic for background
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
