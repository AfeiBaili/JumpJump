package cn.afeibaili.jump.common.tile

import cn.afeibaili.jump.common.Identifier
import kotlin.also


/**
 * # 方块注册
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:38
 */

object Tiles {
    val tileTypeMap = HashMap<Identifier, TileType>()

    val ERROR = register("error")
    val AIR = register("air")
    val DIRT = register("dirt")
    val GRASS_DIRT = register("grass_dirt")

    fun register(id: String): TileType {
        val identifier = Identifier("tile", id)
        return TileType(identifier).also { tileTypeMap[identifier] = it }
    }

    fun getBlockTypeById(identifier: Identifier): TileType {
        val tileType: TileType? = tileTypeMap[identifier]
        if (tileType == null) return ERROR
        return tileType
    }
}