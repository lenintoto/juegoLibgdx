package io.github.lenintoto

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync
import ktx.scene2d.Scene2DSkin

class Main : KtxGame<KtxScreen>() {
    lateinit var batch: SpriteBatch

    override fun create() {
        KtxAsync.initiate()
        batch = SpriteBatch()

        // Configurar Skin con parámetros móviles
        Scene2DSkin.defaultSkin = Skin(Gdx.files.internal("uiskin.json")).apply {
            getFont("default-font").data.setScale(2f) // Escala global de fuente
        }

        addScreen(MenuScreen(this))
        addScreen(GameWithLogoScreen(this))
        setScreen<MenuScreen>()
    }

    override fun dispose() {
        Scene2DSkin.defaultSkin.dispose()
        batch.dispose()
        super.dispose()
    }
}
