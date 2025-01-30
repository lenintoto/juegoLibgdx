//GameWithLogoScreen.kt
package io.github.lenintoto

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Rectangle
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use

class GameWithLogoScreen(private val game: Main) : KtxScreen {
    // Texturas
    private val background = Texture("logo.png".toInternalFile(), true).apply {
        setFilter(Linear, Linear)
    }
    private val playerTexture = Texture("player.png".toInternalFile())
    private val leftBtn = Texture("left_btn.png".toInternalFile())
    private val rightBtn = Texture("right_btn.png".toInternalFile())
    private val upBtn = Texture("up_btn.png".toInternalFile())
    private val downBtn = Texture("down_btn.png".toInternalFile())

    // Sistema de nivel
    private val level = Level()

    // Configuración del jugador
    private val player = Rectangle()
    private val playerSpeed = 300f
    private val playerPosition = Vector2()

    // Configuración de controles
    private val buttonSize = 100f
    private val horizontalSpacing = 30f
    private val verticalSpacing = 30f
    private val marginX = 50f
    private val marginY = 50f

    init {
        // Configurar tamaño del jugador
        player.width = 48f
        player.height = 48f

        // Posición inicial del jugador
        resetPlayerPosition()
    }

    private fun resetPlayerPosition() {
        // Colocar al jugador en una posición inicial segura
        playerPosition.set(64f, (level.levelData.size - 2) * 64f)
        player.x = playerPosition.x
        player.y = playerPosition.y
    }

    override fun show() {
        resetPlayerPosition()
    }

    override fun render(delta: Float) {
        // Guardar posición anterior para manejo de colisiones
        val oldX = playerPosition.x
        val oldY = playerPosition.y

        // Procesar entrada del usuario
        handleInput(delta)

        // Actualizar rectángulo de colisión
        player.x = playerPosition.x
        player.y = playerPosition.y

        // Verificar colisiones
        if (level.checkCollision(player)) {
            playerPosition.x = oldX
            playerPosition.y = oldY
            player.x = oldX
            player.y = oldY
        }

        // Verificar victoria
        if (level.isAtGoal(player)) {
            // Aquí puedes añadir lógica de victoria
            game.setScreen<MenuScreen>()
            return
        }

        // Renderizar
        clearScreen(0.7f, 0.7f, 0.7f)
        game.batch.use { batch ->
            // Renderizar fondo
            batch.draw(background, 0f, 0f,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat())

            // Renderizar nivel
            level.render(batch)

            // Renderizar jugador
            batch.draw(playerTexture,
                playerPosition.x,
                playerPosition.y,
                player.width,
                player.height)

            // Renderizar controles
            renderControls(batch)
        }
    }

    private fun handleInput(delta: Float) {
        if (Gdx.input.isTouched) {
            val touchX = Gdx.input.getX().toFloat()
            val touchY = Gdx.graphics.height - Gdx.input.getY().toFloat()

            // Calcular área total de botones
            val totalWidth = (buttonSize * 3) + (horizontalSpacing * 2)

            // Verificar si el toque está en el área de controles
            if (touchX in marginX..(marginX + totalWidth) &&
                touchY in marginY..(marginY + buttonSize + verticalSpacing + buttonSize)) {

                val movement = playerSpeed * delta

                // Flecha arriba
                if (touchY > marginY + buttonSize + verticalSpacing) {
                    playerPosition.y += movement
                }
                // Fila inferior (izquierda, abajo, derecha)
                else {
                    when {
                        touchX < marginX + buttonSize ->
                            playerPosition.x -= movement // Izquierda
                        touchX < marginX + buttonSize + horizontalSpacing + buttonSize ->
                            playerPosition.y -= movement // Abajo
                        else ->
                            playerPosition.x += movement // Derecha
                    }
                }
            }
        }
    }

    private fun renderControls(batch: SpriteBatch) {
        val totalWidth = (buttonSize * 3) + (horizontalSpacing * 2)

        // Flecha arriba
        batch.draw(upBtn,
            marginX + (totalWidth - buttonSize) / 2,
            marginY + buttonSize + verticalSpacing,
            buttonSize,
            buttonSize)

        // Fila inferior
        batch.draw(leftBtn,
            marginX,
            marginY,
            buttonSize,
            buttonSize)

        batch.draw(downBtn,
            marginX + buttonSize + horizontalSpacing,
            marginY,
            buttonSize,
            buttonSize)

        batch.draw(rightBtn,
            marginX + (buttonSize + horizontalSpacing) * 2,
            marginY,
            buttonSize,
            buttonSize)
    }

    private fun keepPlayerInBounds() {
        playerPosition.x = playerPosition.x.coerceIn(
            0f,
            Gdx.graphics.width.toFloat() - player.width
        )
        playerPosition.y = playerPosition.y.coerceIn(
            0f,
            Gdx.graphics.height.toFloat() - player.height
        )
    }

    override fun resize(width: Int, height: Int) {
        // Puedes añadir lógica de redimensionamiento si es necesario
    }

    override fun dispose() {
        background.disposeSafely()
        playerTexture.disposeSafely()
        leftBtn.disposeSafely()
        rightBtn.disposeSafely()
        upBtn.disposeSafely()
        downBtn.disposeSafely()
        level.dispose()
    }
}
