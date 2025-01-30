package io.github.lenintoto

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.assets.toInternalFile

class Level {
    // Tipos de bloques en el nivel
    enum class BlockType {
        EMPTY, WALL, GOAL
    }

    // Matriz que representa el nivel (0 = vacío, 1 = pared, 2 = meta)
    val levelData = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 0, 0, 1, 0, 0, 1),
        arrayOf(1, 0, 0, 1, 0, 1, 1, 0, 0, 1),
        arrayOf(1, 1, 0, 1, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 1, 1, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 1, 1, 0, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 2, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    )

    private val wallTexture = Texture("wall.png".toInternalFile())
    private val goalTexture = Texture("goal.png".toInternalFile())
    private val blockSize = 64f // Tamaño de cada bloque en píxeles
    private val walls = mutableListOf<Rectangle>()

    init {
        // Crear colliders para las paredes
        for (y in levelData.indices) {
            for (x in levelData[y].indices) {
                if (levelData[y][x] == 1) {
                    walls.add(Rectangle(x * blockSize, (levelData.size - 1 - y) * blockSize,
                        blockSize, blockSize))
                }
            }
        }
    }

    fun render(batch: SpriteBatch) {
        for (y in levelData.indices) {
            for (x in levelData[y].indices) {
                val posY = (levelData.size - 1 - y) * blockSize
                when (levelData[y][x]) {
                    1 -> batch.draw(wallTexture, x * blockSize, posY, blockSize, blockSize)
                    2 -> batch.draw(goalTexture, x * blockSize, posY, blockSize, blockSize)
                }
            }
        }
    }

    fun checkCollision(playerRect: Rectangle): Boolean {
        return walls.any { it.overlaps(playerRect) }
    }

    fun isAtGoal(playerRect: Rectangle): Boolean {
        val goalY = levelData.indexOfFirst { row -> row.contains(2) }
        val goalX = levelData[goalY].indexOf(2)
        val goalRect = Rectangle(goalX * blockSize,
            (levelData.size - 1 - goalY) * blockSize,
            blockSize, blockSize)
        return goalRect.overlaps(playerRect)
    }

    fun dispose() {
        wallTexture.dispose()
        goalTexture.dispose()
    }
}
