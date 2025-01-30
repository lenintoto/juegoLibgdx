# Juego de Laberinto con LibGDX y Kotlin

Un juego de laberinto móvil desarrollado con LibGDX y Kotlin, que implementa controles táctiles y un sistema de niveles adaptable.

# Carpeta de decarga del APK del juego desarrollado

El apk del juego se encuentra en la carpeta con el nombre "carpetaAPK" que se encuentra en la raiz del proyecto.

## 🎮 Características

- Controles táctiles optimizados para dispositivos móviles
- Sistema de niveles dinámico que se adapta a diferentes tamaños de pantalla
- Detección de colisiones
- Sistema de navegación entre menús
- Interfaz de usuario intuitiva
- Sistema de victoria al alcanzar la meta

## 🛠️ Requisitos Técnicos

- JDK 8 o superior
- Android SDK (para compilación Android)
- Gradle 7.0 o superior
- LibGDX 1.11.0
- Kotlin 1.6.0 o superior

## 📝 Dependencias Principales

gradle
dependencies {
    implementation "com.badlogicgames.gdx:gdx:1.11.0"
    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.6.0"
    implementation "io.github.libktx:ktx-app:1.11.0-rc1"
    implementation "io.github.libktx:ktx-graphics:1.11.0-rc1"
    implementation "io.github.libktx:ktx-scene2d:1.11.0-rc1"
}


## 🎨 Recursos Necesarios

El juego requiere los siguientes archivos de recursos en el directorio assets/:

- logo.png - Logo del juego
- player.png - Sprite del jugador
- wall.png - Textura para las paredes
- goal.png - Textura para la meta
- left_btn.png - Botón de movimiento izquierda
- right_btn.png - Botón de movimiento derecha
- up_btn.png - Botón de movimiento arriba
- down_btn.png - Botón de movimiento abajo
- uiskin.json - Skin para la interfaz de usuario

## 🚀 Instalación y Ejecución

1. Clonar el repositorio:
bash
git clone https://github.com/lenintoto/juegoLibgdx.git


2. Abrir el proyecto en Android Studio o IntelliJ IDEA

3. Sincronizar el proyecto con Gradle

4. Ejecutar el proyecto:

## 📱 Controles

El juego utiliza un sistema de control táctil con cuatro botones direccionales:
- Flecha arriba: Mover hacia arriba
- Flecha abajo: Mover hacia abajo
- Flecha izquierda: Mover hacia la izquierda
- Flecha derecha: Mover hacia la derecha

## 🏗️ Estructura del Proyecto


src/
├── main/
│   ├── kotlin/io/github/lenintoto/
│   │   ├── Main.kt
│   │   ├── MenuScreen.kt
│   │   ├── GameWithLogoScreen.kt
│   │   └── Level.kt
│   └── assets/
│       ├── textures/
│       └── ui/
├── android/
└── desktop/


## 🎯 Objetivos del Juego

- Navegar a través del laberinto evitando las paredes
- Alcanzar la meta (marcada con una textura especial)
- Completar el nivel en el menor tiempo posible

## 🔧 Personalización

### Modificar el Nivel
El nivel se puede personalizar modificando la matriz levelData en la clase Level.kt:
- 0: Espacio vacío
- 1: Pared
- 2: Meta

Ejemplo:
kotlin
val levelData = arrayOf(
    arrayOf(1, 1, 1, 1, 1),
    arrayOf(1, 0, 0, 0, 1),
    arrayOf(1, 0, 1, 0, 1),
    arrayOf(1, 0, 0, 2, 1),
    arrayOf(1, 1, 1, 1, 1)
)


## ✨ Autores

- Lenin Gomez
- Steven Castillo 
- Dilan Bedoya  
- Alexis Farinango 
- Alex Cardenas 
