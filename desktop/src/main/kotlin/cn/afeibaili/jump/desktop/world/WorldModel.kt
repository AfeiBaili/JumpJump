package cn.afeibaili.jump.desktop.world

import cn.afeibaili.gl.image.TextureAtlas
import cn.afeibaili.jump.common.map.World
import cn.afeibaili.jump.common.resource.ResourceFileGetter


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
        val blocksTexture =
            TextureAtlas.create("block", ResourceFileGetter.getResourceFileList("tile"))

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