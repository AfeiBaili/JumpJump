package cn.afeibaili.jump.common.world

import cn.afeibaili.jump.common.block.Block
import cn.afeibaili.jump.common.block.BlockType
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
        val layer: Layer = getLayerAt(layerIndex)
        val chunkX = getChunkX(x.toFloat())
        val chunkY = getChunkY(y.toFloat())
        val chunk: Chunk? = layer.getChunkAt(chunkX, chunkY)
        val blockX = getBlockX(x)
        val blockY = getBlockY(y)
        return chunk?.getBlockAt(blockX, blockY)
    }

    fun setBlockAt(layerIndex: Int, x: Int, y: Int, blockType: BlockType) {
        if (layerIndex < 0 || layerIndex >= layers.size) return
        val layer: Layer = getLayerAt(layerIndex)
        val chunkX = getChunkX(x.toFloat())
        val chunkY: Int = getChunkY(y.toFloat())
        val chunk: Chunk? = layer.getChunkAt(chunkX, chunkY)
        val blockX = getBlockX(x)
        val blockY = getBlockY(y)
        chunk?.setBlockAt(blockX, blockY, blockType)
    }

    fun getLayerAt(layerIndex: Int): Layer = layers[layerIndex]
    fun getChunkX(x: Float): Int = floor(x / Chunk.CHUNK_SIDE).toInt()
    fun getChunkY(y: Float): Int = floor(y / Chunk.CHUNK_SIDE).toInt()
    fun getBlockX(x: Int): Int = x % Chunk.CHUNK_SIDE
    fun getBlockY(y: Int): Int = y % Chunk.CHUNK_SIDE
}