package cn.afeibaili.jump.desktop.render

import cn.afeibaili.jump.common.util.createLogger


/**
 * # 渲染器系统
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:49
 */

class RendererSystem {
    private val logger = createLogger { "RendererSystem" }
    val worldRenderer = WorldRenderer()
    val camera get() = worldRenderer.camera

    fun init() {
        logger.info("initialize world renderer")
        worldRenderer.init()
    }

    fun frame() {
        worldRenderer.render()
    }
}