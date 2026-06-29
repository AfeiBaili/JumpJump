package cn.afeibaili.jump.common.block

import cn.afeibaili.jump.common.Identifier
import cn.afeibaili.jump.common.util.createLogger


/**
 * # 方块注册
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:38
 */

object Blocks {
    val blockTypeMap = HashMap<Identifier, BlockType>()
    private val logger = createLogger { "Tiles" }

    val ERROR = register("error")
    val AIR = register("air")
    val DIRT = register("dirt")
    val GRASS_DIRT = register("grass_dirt")
    val GRASS = register("grass")

    fun register(id: String): BlockType {
        val identifier = Identifier("tile", id)
        logger.info("registering $identifier")
        return BlockType(identifier).also { blockTypeMap[identifier] = it }
    }

    fun getBlockTypeById(identifier: Identifier): BlockType {
        val blockType: BlockType? = blockTypeMap[identifier]
        if (blockType == null) return ERROR
        return blockType
    }
}