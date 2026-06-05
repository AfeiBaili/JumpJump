package cn.afeibaili.jump.common.map

import cn.afeibaili.jump.common.resource.MapLoader
import cn.afeibaili.jump.common.util.createLogger


/**
 * # 存放地图数据
 *
 * @author AfeiBaili
 * @version 2026/6/2 23:52
 */

object MapManager {
    private val logger = createLogger { "MapManager" }
    lateinit var maps: List<Map>

    fun load() {
        logger.info("loading maps...")
        maps = MapLoader.load()
    }
}