package cn.afeibaili.jump.common.tile

import cn.afeibaili.jump.common.Identifier
import cn.afeibaili.jump.common.util.createLogger


/**
 * # 方块注册
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:38
 */

object Tiles {
    val tileTypeMap = HashMap<Identifier, TileType>()
    private val logger = createLogger { "Tiles" }

    val ERROR = register("error")
    val AIR = register("air")
    val DIRT = register("dirt")
    val GRASS_DIRT = register("grass_dirt")
    val GRASS = register("grass")

    fun register(id: String): TileType {
        val identifier = Identifier("tile", id)
        logger.info("registering $identifier")
        return TileType(identifier).also { tileTypeMap[identifier] = it }
    }

    fun getBlockTypeById(identifier: Identifier): TileType {
        val tileType: TileType? = tileTypeMap[identifier]
        if (tileType == null) return ERROR
        return tileType
    }
}