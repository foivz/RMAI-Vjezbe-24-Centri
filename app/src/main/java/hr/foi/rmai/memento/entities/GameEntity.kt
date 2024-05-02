package hr.foi.rmai.memento.entities

import android.content.Context

abstract class GameEntity(context: Context, width: Int, height: Int) {
    protected val sizeX = 128
    protected val sizeY = 128

    public var x = 0
    public var y = 0

    var maxX = 0
    var minX = 0
    var maxY = 0
    var minY = 0

    public var speed = 0
    public var playerSpeed = 0

    init {
        maxX = width
        maxY = height
    }

    abstract fun update()
}