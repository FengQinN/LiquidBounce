/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 *
 * Copyright (c) 2016 - 2023 CCBlueX
 *
 * LiquidBounce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LiquidBounce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LiquidBounce. If not, see <https://www.gnu.org/licenses/>.
 */
package net.ccbluex.liquidbounce.base.ultralight

import com.labymedia.ultralight.UltralightJava
import com.labymedia.ultralight.config.FontHinting
import com.labymedia.ultralight.config.UltralightConfig
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil
import com.labymedia.ultralight.os.OperatingSystem
import com.labymedia.ultralight.util.UltralightGlfwOpenGLContext
import com.labymedia.ultralight.util.UltralightGlfwOpenGLGPUDriver
import com.labymedia.ultralight.util.UltralightGlfwOpenGLWindow
import com.mojang.blaze3d.systems.RenderSystem
import net.ccbluex.liquidbounce.base.ultralight.filesystem.BrowserFileSystem
import net.ccbluex.liquidbounce.base.ultralight.glfw.GlfwClipboardAdapter
import net.ccbluex.liquidbounce.base.ultralight.glfw.GlfwCursorAdapter
import net.ccbluex.liquidbounce.base.ultralight.glfw.GlfwInputAdapter
import net.ccbluex.liquidbounce.base.ultralight.hooks.UltralightIntegrationHook
import net.ccbluex.liquidbounce.base.ultralight.hooks.UltralightScreenHook
import net.ccbluex.liquidbounce.base.ultralight.js.bindings.UltralightStorage
import net.ccbluex.liquidbounce.render.engine.ShaderProgram
import net.ccbluex.liquidbounce.utils.client.logger
import net.ccbluex.liquidbounce.utils.client.mc
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack


object UltralightEngine {

    /**
     * Ultralight window
     *
     * Might be useful in the future for external UI.
     */
    var windowHandle = 0L

    lateinit var window: UltralightGlfwOpenGLWindow
    lateinit var gpuDriver: UltralightGlfwOpenGLGPUDriver

    lateinit var texShader: ShaderProgram

    /**
     * Ultralight resources
     */
    private val resources = UltralightResources()

    /**
     * Glfw
     */
    lateinit var clipboardAdapter: GlfwClipboardAdapter
    lateinit var cursorAdapter: GlfwCursorAdapter
    lateinit var inputAdapter: GlfwInputAdapter

    /**
     * Views
     */
    val activeView: View?
        get() = views.find { it is ScreenView && mc.currentScreen == it.screen }

    private val views = mutableListOf<View>()

    fun initHandle(handle: Long) {
        windowHandle = handle
    }

    /**
     * Initializes the platform
     */
    fun init(width: Int, height: Int) {
        logger.info("Loading ultralight...")

        // Check resources
        logger.info("Checking resources...")
        resources.downloadResources()

        // Load natives from native directory inside root folder
        loadNatives()

        // Setup platform
        println("Setting up ultralight")

        println("${RenderSystem.isOnRenderThread()}")

        gpuDriver = UltralightGlfwOpenGLGPUDriver.create(true)
        window = UltralightGlfwOpenGLWindow.create(
            UltralightGlfwOpenGLContext.create(
                width,
                height,
                "Minecraft",
                gpuDriver,
                windowHandle
            ),
            width,
            height,
            "Ultralight"
        )

        // Setup GLFW adapters
        clipboardAdapter = GlfwClipboardAdapter()
        cursorAdapter = GlfwCursorAdapter()
        inputAdapter = GlfwInputAdapter()

        window.post {
            window.context.platform.setConfig(
                UltralightConfig()
                    .cachePath(resources.cacheRoot.absolutePath)
                    .fontHinting(FontHinting.SMOOTH)
            )
            window.context.platform.usePlatformFontLoader()
            window.context.platform.setFileSystem(BrowserFileSystem())
            // window.context.platform.setClipboard(GlfwClipboardAdapter())

            window.view.loadURL("https://liquidbounce.net")
       }

        // Setup hooks
        UltralightIntegrationHook
        UltralightScreenHook

        UltralightStorage

        logger.info("Successfully loaded ultralight!")
    }

    fun loadNatives() {
        logger.info("Loading ultralight natives")
        val natives = resources.binRoot.toPath()
        logger.info("Native path: $natives")

        val libs = listOf(
            "glib-2.0-0",
            "gobject-2.0-0",
            "gmodule-2.0-0",
            "gio-2.0-0",
            "gstreamer-full-1.0",
            "gthread-2.0-0"
        )
        println("Libraries: $libs")

        val os = OperatingSystem.get()
        for (lib in libs) {
            println("Loading library $lib")
            System.load(natives.resolve(os.mapLibraryName(lib)).toAbsolutePath().toString())
        }

        println("Loading UltralightJava")
        UltralightJava.load(natives)
        println("Loading UltralightGPUDriver")
        UltralightGPUDriverNativeUtil.load(natives)
    }

    fun shutdown() {
        cursorAdapter.cleanup()
    }

    fun update() {
        window.updateWebContent()
        window.context.updateJavaScript()
    }

    fun render(screenLayer: RenderLayer, matrices: MatrixStack) {
        views.forEach(View::render)
    }

    fun resize(width: Long, height: Long) {
        views.forEach { it.resize(width, height) }
    }

    fun newSplashView() = View(RenderLayer.SPLASH_LAYER).also { views += it }

    fun newOverlayView() =
        View(RenderLayer.OVERLAY_LAYER).also { views += it }

    fun newScreenView(screen: Screen, adaptedScreen: Screen? = null, parentScreen: Screen? = null) =
        ScreenView(screen, adaptedScreen, parentScreen).also { views += it }

    fun removeView(view: View) {
        view.free()
        views.remove(view)
    }

}

enum class RenderLayer {
    OVERLAY_LAYER, SCREEN_LAYER, SPLASH_LAYER
}
