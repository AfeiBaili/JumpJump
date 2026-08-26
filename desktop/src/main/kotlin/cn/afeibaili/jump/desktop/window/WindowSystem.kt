package cn.afeibaili.jump.desktop.window

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.input.Key
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.jump.common.identifier
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.input.KeySet
import org.lwjgl.glfw.GLFW

/**
 * # 窗口系统管理
 *
 * @author AfeiBaili
 * @version 2026/6/6 20:43
 */

class WindowSystem(val window: Window = Application.window) {
    val keySet = KeySet("system" identifier "window")

    fun loadKeyBind() {
        keySet.bind(Key("vsync", GLFW.GLFW_KEY_F1)) {
            keyClick {
                window.vsync = !window.vsync
            }
        }
    }

    fun init() {
        loadKeyBind()
        keySet.onKey()

        GLFW.glfwSetWindowSizeCallback(window.windowLocation) { _, w, h ->
            val h = if (h == 0) 1 else h
            val aspect = w.toFloat() / h.toFloat()
            window.setViewport(w, h)
            Application.rendererSystem.worldRenderer.camera // 世界摄像机
                .ortho(-5f * aspect, 5f * aspect, -5f, 5f, -1f, 1f)

            // setWidthHeightOrtho(Application.rendererSystem.debugRenderer.textCamera, 0f, w.toFloat(), 0f, h.toFloat())
            setWidthHeightOrtho(Application.rendererSystem.uiRenderer.rectCamera, 0f, w.toFloat(), h.toFloat(), 0f)
            setWidthHeightOrtho(Application.rendererSystem.uiRenderer.textCamera, 0f, w.toFloat(), h.toFloat(), 0f)

            Application.screen.update(w.toFloat(), h.toFloat()) //屏幕大小更新
            Application.rendererSystem.uiRenderer.update() // ui数据更新
        }

        GLFW.glfwSetWindowCloseCallback(window.windowLocation) {
            Application.stop()
        }
    }

    private fun setWidthHeightOrtho(camera: Camera, left: Float, right: Float, bottom: Float, top: Float) {
        camera.ortho(left, right, bottom, top, -1f, 0f)
    }
}