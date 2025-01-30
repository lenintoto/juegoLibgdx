package io.github.lenintoto

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.scene2d.*

class MenuScreen(private val game: Main) : KtxScreen {
    private val stage = Stage(ScreenViewport()) // Viewport dinámico para móviles ⚡
    private val skin: Skin = Scene2DSkin.defaultSkin

    override fun show() {
        stage.clear()
        stage.addActor(scene2d.table(skin) {
            setFillParent(true)
            defaults().pad(20f).fillX()

            label("MENU PRINCIPAL", "default") {
                it.padBottom(40f)
                setAlignment(Align.center)
            }
            row()

            textButton("JUGAR", "default") {
                onClick { game.setScreen<GameWithLogoScreen>() }
                it.height(100f) // Altura táctil para móviles
                label.setFontScale(2f) // Escala de fuente aumentada
            }
            row()

            textButton("SALIR", "default") {
                onClick { Gdx.app.exit() }
                it.height(100f)
                label.setFontScale(2f)
            }
        })

        Gdx.input.inputProcessor = stage
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true) // Ajusta a cambios de orientación
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
    }
}
