package cn.afeibaili.jump.desktop.window

import cn.afeibaili.gl.Window
import cn.afeibaili.jump.desktop.Application
import org.lwjgl.glfw.GLFW


/**
 * # 窗口系统管理
 *
 * @author AfeiBaili
 * @version 2026/6/6 20:43
 */

class WindowSystem(val window: Window) {
    fun init() {
        GLFW.glfwSetWindowSizeCallback(window.windowLocation) { _, w, h ->
            val h = if (h == 0) 1 else h
            val aspect = w.toFloat() / h.toFloat()
            window.setViewport(w, h)
            Application.rendererSystem.worldRenderer.camera
                .ortho(-5f * aspect, 5f * aspect, -5f, 5f, -1f, 1f)
        }
    }
}