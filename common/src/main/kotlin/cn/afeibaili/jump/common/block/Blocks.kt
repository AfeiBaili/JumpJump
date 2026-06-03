package cn.afeibaili.jump.common.block

import cn.afeibaili.jump.common.Identifier
import kotlin.also


/**
 * # 方块注册
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:38
 */

object Blocks {
    val blockTypeMap = HashMap<Identifier, BlockType>()

    val ERROR = register("error")
    val AIR = register("air")
    val DIRT = register("dirt")
    val GRASS_DIRT = register("grass_dirt")

    fun register(id: String): BlockType {
        val identifier = Identifier("block", id)
        return BlockType(identifier).also { blockTypeMap[identifier] = it }
    }

    fun getBlockTypeById(identifier: Identifier): BlockType {
        val blockType: BlockType? = blockTypeMap[identifier]
        if (blockType == null) return ERROR
        return blockType
    }
}