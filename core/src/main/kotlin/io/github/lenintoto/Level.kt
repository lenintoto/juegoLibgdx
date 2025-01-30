//Level.kt (corregido)
package io.github.lenintoto

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import ktx.assets.toInternalFile

class Level {
    // Define un nivel más grande para mejor aprovechamiento del espacio
    val levelData = arrayOf(
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1),
        arrayOf(1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1),
        arrayOf(1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1),
        arrayOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1),
        arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1),
        arrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1),
        arrayOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    )

    private val wallTexture = Texture("wall.png".toInternalFile())
    private val goalTexture = Texture("goal.png".toInternalFile())

    // Variables para el escalado dinámico
    private var blockSize: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private val walls = mutableListOf<Rectangle>()

    init {
        updateDimensions()
    }

    fun updateDimensions() {
        // Calcular el tamaño de bloque basado en el tamaño de la pantalla
        val screenWidth = Gdx.graphics.width.toFloat()
        val screenHeight = Gdx.graphics.height.toFloat()

        // Reservar espacio para los controles (aproximadamente 200 píxeles desde abajo)
        val playableHeight = screenHeight - 200

        // Calcular el tamaño de bloque que mejor se ajuste
        blockSize = minOf(
            screenWidth / levelData[0].size,
            playableHeight / levelData.size
        )

        // Calcular offsets para centrar el nivel
        offsetX = (screenWidth - (levelData[0].size * blockSize)) / 2
        offsetY = ((playableHeight - (levelData.size * blockSize)) / 2) + 200 // Añadir espacio para controles

        // Actualizar rectángulos de colisión
        updateCollisionBoxes()
    }

    private fun updateCollisionBoxes() {
        walls.clear()
        for (y in levelData.indices) {
            for (x in levelData[y].indices) {
                if (levelData[y][x] == 1) {
                    walls.add(Rectangle(
                        offsetX + x * blockSize,
                        offsetY + (levelData.size - 1 - y) * blockSize,
                        blockSize,
                        blockSize
                    ))
                }
            }
        }
    }

    fun render(batch: SpriteBatch) {
        for (y in levelData.indices) {
            for (x in levelData[y].indices) {
                val posX = offsetX + x * blockSize
                val posY = offsetY + (levelData.size - 1 - y) * blockSize

                when (levelData[y][x]) {
                    1 -> batch.draw(wallTexture, posX, posY, blockSize, blockSize)
                    2 -> batch.draw(goalTexture, posX, posY, blockSize, blockSize)
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
        val goalRect = Rectangle(
            offsetX + goalX * blockSize,
            offsetY + (levelData.size - 1 - goalY) * blockSize,
            blockSize,
            blockSize
        )
        return goalRect.overlaps(playerRect)
    }

    fun getInitialPlayerPosition(): Vector2 {
        // Encontrar una posición inicial válida (primer espacio vacío desde arriba)
        for (y in levelData.indices) {
            for (x in levelData[y].indices) {
                if (levelData[y][x] == 0) {
                    return Vector2(
                        offsetX + x * blockSize,
                        offsetY + (levelData.size - 1 - y) * blockSize
                    )
                }
            }
        }
        return Vector2(offsetX + blockSize, offsetY + blockSize)
    }

    fun getBlockSize(): Float = blockSize

    fun dispose() {
        wallTexture.dispose()
        goalTexture.dispose()
    }
}
