package hr.foi.rmai.memento

import android.graphics.Canvas
import android.view.SurfaceHolder
import hr.foi.rmai.memento.views.GameView

class GameThread(val surfaceHolder: SurfaceHolder, val gameView: GameView) : Thread() {
    @Volatile
    public var running = true

    val fpsTarget:Long = 60
    val targetSleep = (1000 / fpsTarget)

    override fun run() {
        while (running) {
            val canvas: Canvas? = surfaceHolder.lockCanvas()

            if (canvas != null) {
                gameView.update()
                gameView.draw(canvas)

                surfaceHolder.unlockCanvasAndPost(canvas)
            }

            controlFPS()
        }
    }

    private fun controlFPS() {
        try {
            sleep(targetSleep)
        } catch (e: InterruptedException) {
        }
    }
}