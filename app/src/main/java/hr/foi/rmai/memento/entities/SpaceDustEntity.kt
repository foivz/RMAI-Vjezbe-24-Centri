package hr.foi.rmai.memento.entities

import android.content.Context
import java.util.Random

class SpaceDustEntity(context: Context, width: Int, height: Int):
    GameEntity(context, width, height) {

    var speed = 0
    val generator = Random()

    init {
        speed = generator.nextInt(10)
        x = generator.nextInt(maxX)
        y = generator.nextInt(maxY)
    }

    override fun update() {
        x -= speed

        if (x < minX) {
            x = maxX
            y = generator.nextInt(maxY)
            speed = generator.nextInt(10)
        }
    }

}