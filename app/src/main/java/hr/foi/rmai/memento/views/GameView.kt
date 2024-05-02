package hr.foi.rmai.memento.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceView
import hr.foi.rmai.memento.entities.PlayerGameEntity
import hr.foi.rmai.memento.entities.SpaceDustEntity

class GameView(context: Context, width: Int, height: Int) : SurfaceView(context) {
    private val paint: Paint = Paint()
    private val player: PlayerGameEntity

    private val spaceDustList = ArrayList<SpaceDustEntity>()

    private val spaceDustNum = 30

    init {
        player = PlayerGameEntity(context, width, height)

        for (i in 1..spaceDustNum) {
            spaceDustList.add(SpaceDustEntity(context, width, height))
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


            canvas.drawBitmap(player.bitmap, player.x.toFloat(), player.y.toFloat(), paint)
        }
    }

    public fun update() {
        player.update()

        spaceDustList.forEach { spaceDust ->
            spaceDust.update()
        }
    }
}