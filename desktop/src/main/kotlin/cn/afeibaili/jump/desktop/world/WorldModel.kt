package cn.afeibaili.jump.desktop.world

import cn.afeibaili.gl.image.TextureAtlas
import cn.afeibaili.jump.common.world.World
import cn.afeibaili.jump.desktop.render.TextureManager


/**
 * # 世界、关卡实例
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:43
 */

class WorldModel private constructor(
    val world: World,
    val blockModels: List<List<BlockModel>>,
    val size: Int,
    val atlas: TextureAtlas,
) {
    companion object {
        val blocksTexture get() = TextureManager.blockTextureAtlas

        fun of(world: World): WorldModel {
            val blockModels = ArrayList<ArrayList<BlockModel>>()

            world.blocks.forEach { tileLine ->
                val blockModelLine = ArrayList<BlockModel>()
                tileLine.forEach { tile ->
                    val uv = FloatArray(4)
                    blocksTexture.getUv(tile.type.identifier.id, uv)
                    blockModelLine.add(BlockModel(tile, uv))
                }
                blockModels.add(blockModelLine)
            }

            val size: Int = world.blocks.sumOf { tileLine -> tileLine.size }

            return WorldModel(world, blockModels, size, blocksTexture)
        }
    }
}