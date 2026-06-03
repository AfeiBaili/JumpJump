package cn.afeibaili.jump.common.world

import cn.afeibaili.jump.common.resource.WorldLoader
import cn.afeibaili.jump.common.util.createLogger


/**
 * # 存放地图数据
 *
 * @author AfeiBaili
 * @version 2026/6/2 23:52
 */

object WorldManager {
    private val logger = createLogger { "Worlds" }
    lateinit var worlds: List<World>

    fun load() {
        logger.info("loading worlds...")
        worlds = WorldLoader.load()
    }
}