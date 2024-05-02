package hr.foi.rmai.memento.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView
import hr.foi.rmai.memento.database.TasksDatabase
import hr.foi.rmai.memento.entities.EnemyGameEntity
import hr.foi.rmai.memento.entities.PlayerGameEntity
import hr.foi.rmai.memento.entities.SpaceDustEntity

class GameView(context: Context, width: Int, height: Int) : SurfaceView(context) {
    private val paint: Paint = Paint()
    private val player: PlayerGameEntity

    private val enemyList = ArrayList<EnemyGameEntity>()
    private val spaceDustList = ArrayList<SpaceDustEntity>()

    private val spaceDustNum = 30

    init {
        player = PlayerGameEntity(context, width, height)

        for (i in 1..spaceDustNum) {
            spaceDustList.add(SpaceDustEntity(context, width, height))
        }

        TasksDatabase.getInstance().getTaskCategoriesDao()
            .getAllCategories().forEach { taskCategory ->
                enemyList.add(EnemyGameEntity(context, width, height, taskCategory.name))
            }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        if (holder.surface.isValid) {
            canvas.drawColor(Color.argb(255, 0, 0, 0))

            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 100f
            paint.strokeWidth = 10f

            spaceDustList.forEach { spaceDust ->
                canvas.drawPoint(spaceDust.x.toFloat(), spaceDust.y.toFloat(), paint)
            }

            enemyList.forEach { enemy ->
                canvas.drawText(enemy.enemyTitle, enemy.x.toFloat(), enemy.y.toFloat(), paint)
            }

            canvas.drawBitmap(player.bitmap, player.x.toFloat(), player.y.toFloat(), paint)
        }
    }

    public fun update() {
        player.update()

        spaceDustList.forEach { spaceDust ->
            spaceDust.update()
        }

        enemyList.forEach { enemy ->
            enemy.playerSpeed = player.speed
            enemy.update()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
       when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> {
                player.boosting = false
            }
           MotionEvent.ACTION_DOWN -> {
                player.boosting = true
           }
       }

        return true
    }
}