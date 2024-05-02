package hr.foi.rmai.memento.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import hr.foi.rmai.memento.R

class PlayerGameEntity(context: Context, width: Int, height: Int):
                        GameEntity(context, width, height)
{
    lateinit var bitmap: Bitmap

    init {
        bitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.ship),
            sizeX, sizeY, false
        )

        x = 700
    }

    override public fun update() {
        if (x > minX) {
            x--
        }
    }
}