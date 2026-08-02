package cn.afeibaili.jump.desktop.render

import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.render.text.DebugRenderer


/**
 * # 渲染器系统
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:49
 */

class RendererSystem {
    private val logger = createLogger { "RendererSystem" }
    val worldRenderer = WorldRenderer()
    val playerRenderer = PlayerRenderer()
    val blockRenderer = BlockRenderer()
    val debugRenderer = DebugRenderer()

    fun init() {
        logger.info("initialize world renderer")
        worldRenderer.init()
        logger.info("initialize block renderer")
        blockRenderer.init()
        logger.info("initialize debug text renderer")
        debugRenderer.init()
    }

    fun frame() {
        blockRenderer.update()
        worldRenderer.render()
        playerRenderer.render()
        debugRenderer.render()
    }
}