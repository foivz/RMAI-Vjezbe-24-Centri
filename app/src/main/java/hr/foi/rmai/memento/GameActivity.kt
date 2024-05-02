package hr.foi.rmai.memento

import android.os.Bundle
import android.view.SurfaceHolder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator
import hr.foi.rmai.memento.views.GameView

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private var gameThread: GameThread? = null
    private lateinit var surfaceHolder: SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowMetrics: WindowMetrics = WindowMetricsCalculator.getOrCreate().
                        computeCurrentWindowMetrics(this)
        val height = windowMetrics.bounds.height()
        val width = windowMetrics.bounds.width()

        gameView = GameView(this, width, height)
        surfaceHolder = gameView.holder
        surfaceHolder.addCallback(surfaceCallback)

        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()

        gameThread?.running = true
    }

    override fun onPause() {
        super.onPause()

        gameThread?.running = false
    }

    private val surfaceCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            gameThread = GameThread(holder, gameView)
            gameThread?.start()
        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            try {
                gameThread?.join()
            } catch (e: InterruptedException) {

            }
        }

    }
}