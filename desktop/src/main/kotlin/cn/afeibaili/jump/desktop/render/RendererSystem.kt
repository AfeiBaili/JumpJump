package cn.afeibaili.jump.desktop.render

import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.desktop.render.text.FpsTimer


/**
 * # 渲染器系统
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:49
 */

class RendererSystem {
    private val logger = logger { "RendererSystem" }
    val worldRenderer = WorldRenderer()
    val playerRenderer = PlayerRenderer()
    val blockRenderer = BlockRenderer()
    val debugRenderer = DebugRenderer()
    val uiRenderer = UIRenderer()
    val fps = FpsTimer()

    fun init() {
        logger.info("initialize world renderer")
        worldRenderer.init()
        logger.info("initialize block renderer")
        blockRenderer.init()
        logger.info("initialize debug renderer")
        debugRenderer.init()
        logger.info("initialize ui renderer")
        uiRenderer.init()
    }

    fun frame() {
        fps.update()
        blockRenderer.update()
        worldRenderer.render()
        playerRenderer.render()
        debugRenderer.render()
        uiRenderer.render()
    }
}