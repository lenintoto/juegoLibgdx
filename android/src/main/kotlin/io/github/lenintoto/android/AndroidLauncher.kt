package io.github.lenintoto.android

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import io.github.lenintoto.Main

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration().apply {
            // Usar la pantalla completa
            useImmersiveMode = true
            // Mantener la pantalla encendida
            useWakelock = true
        }
        initialize(Main(), config)
    }
}