// GameWithLogoScreen.kt
package io.github.lenintoto

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.math.Vector2
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use

class GameWithLogoScreen(private val game: Main) : KtxScreen {
    private val background = Texture("logo.png".toInternalFile(), true).apply {
        setFilter(Linear, Linear)
    }
    private val playerTexture = Texture("player.png".toInternalFile())

    // Nuevas texturas para botones
    private val leftBtn = Texture("left_btn.png".toInternalFile())
    private val rightBtn = Texture("right_btn.png".toInternalFile())
    private val upBtn = Texture("up_btn.png".toInternalFile())
    private val downBtn = Texture("down_btn.png".toInternalFile())
    private val horizontalSpacing = 30f
    private val verticalSpacing = 30f

    private val playerSpeed = 300f
    private val playerPosition = Vector2()
    private val buttonSize = 100f // Tamaño de los botones

    override fun show() {
        playerPosition.set(
            (Gdx.graphics.width - playerTexture.width) / 2f,
            (Gdx.graphics.height - playerTexture.height) / 2f
        )
    }

    override fun render(delta: Float) {
        handleInput(delta)
        keepPlayerInBounds()

        clearScreen(0.7f, 0.7f, 0.7f)
        game.batch.use {
            // Fondo y jugador (sin cambios)
            it.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
            it.draw(playerTexture, playerPosition.x, playerPosition.y)

            // Configuración de posición para T invertida izquierda
            val marginX = 50f // Margen desde el borde izquierdo
            val marginY = 50f // Margen desde el borde inferior
            val totalWidth = (buttonSize * 3) + (horizontalSpacing * 2)

            // Flecha arriba (parte superior de la T)
            it.draw(upBtn,
                marginX + (totalWidth - buttonSize) / 2, // Centrado sobre la fila inferior
                marginY + buttonSize + verticalSpacing,
                buttonSize,
                buttonSize
            )

            // Fila inferior (izquierda, abajo, derecha)
            it.draw(leftBtn, marginX, marginY, buttonSize, buttonSize)
            it.draw(downBtn, marginX + buttonSize + horizontalSpacing, marginY, buttonSize, buttonSize)
            it.draw(rightBtn, marginX + (buttonSize + horizontalSpacing) * 2, marginY, buttonSize, buttonSize)
        }
    }

    private fun handleInput(delta: Float) {
        if (Gdx.input.isTouched) {
            val touchX = Gdx.input.getX().toFloat()
            val touchY = Gdx.graphics.height - Gdx.input.getY().toFloat()

            // Coordenadas de los botones
            val marginX = 50f
            val marginY = 50f
            val totalWidth = (buttonSize * 3) + (horizontalSpacing * 2)

            // Verificar si el toque está en el área de botones
            if (touchX in marginX..(marginX + totalWidth) &&
                touchY in marginY..(marginY + buttonSize + verticalSpacing + buttonSize)) {

                // Flecha arriba
                if (touchY > marginY + buttonSize + verticalSpacing) {
                    playerPosition.y += playerSpeed * delta
                }
                // Fila inferior
                else {
                    when {
                        touchX < marginX + buttonSize -> playerPosition.x -= playerSpeed * delta // Izquierda
                        touchX < marginX + buttonSize + horizontalSpacing + buttonSize -> playerPosition.y -= playerSpeed * delta // Abajo
                        else -> playerPosition.x += playerSpeed * delta // Derecha
                    }
                }
            }
        }
    }


    private fun keepPlayerInBounds() {
        playerPosition.x = playerPosition.x.coerceIn(
            0f,
            Gdx.graphics.width.toFloat() - playerTexture.width
        )
        playerPosition.y = playerPosition.y.coerceIn(
            0f,
            Gdx.graphics.height.toFloat() - playerTexture.height
        )
    }

    override fun dispose() {
        background.disposeSafely()
        playerTexture.disposeSafely()
        // Liberar texturas de botones
        leftBtn.disposeSafely()
        rightBtn.disposeSafely()
        upBtn.disposeSafely()
        downBtn.disposeSafely()
    }
}
