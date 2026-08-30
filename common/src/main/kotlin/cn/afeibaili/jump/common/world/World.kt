package cn.afeibaili.jump.common.world

import cn.afeibaili.jump.common.block.Block
import kotlin.math.floor


/**
 * # 世界类，一个世界一个关卡
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:33
 */

class World(
    val name: String,
    val layers: Array<Layer> = arrayOf(
        Layer("player"), Layer("background1"), Layer("background2"),
    ),
) {
    override fun toString(): String {
        return "world name: $name, layer count: ${layers.size}, chunk size: ${layers[0].chunks.size}"
    }

    fun getBlockAt(layerIndex: Int, x: Int, y: Int): Block? {
        if (layerIndex < 0 || layerIndex >= layers.size) return null
        val layer: Layer = layers[layerIndex]
        val chunkX = floor(x.toFloat() / Chunk.CHUNK_SIDE).toInt()
        val chunkY = floor(y.toFloat() / Chunk.CHUNK_SIDE).toInt()
        val chunk: Chunk? = layer.getChunkAt(chunkX, chunkY)
        val blockX = x % Chunk.CHUNK_SIDE
        val blockY = y % Chunk.CHUNK_SIDE
        return chunk?.getBlockAt(blockX, blockY)
    }
}