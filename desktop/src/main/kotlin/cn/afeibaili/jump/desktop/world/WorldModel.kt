package cn.afeibaili.jump.desktop.world

import cn.afeibaili.gl.image.Texture
import cn.afeibaili.gl.image.TextureAtlas
import cn.afeibaili.jump.common.world.World
import cn.afeibaili.jump.desktop.block.BlockModel
import cn.afeibaili.jump.desktop.block.BlockUv
import cn.afeibaili.jump.desktop.render.texture.TextureManager

/**
 * # 世界、关卡实例
 *
 * 使用of伴生对象方法返回此对象
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:43
 */

class WorldModel private constructor(
    val world: World,
    val blockModels: List<BlockAtlas>,
    val size: Int,
    val textureAtlas: TextureAtlas,
) {
    companion object {
        val blockTextureAtlas get() = TextureManager.blockTextureAtlas
        val textureSideMap get() = TextureManager.textureSideMap

        fun of(world: World): WorldModel {
            val blockIndexModelMap =
                mutableMapOf<TextureAtlas.Index, Pair<Texture, MutableList<BlockModel>>>()

            world.blocks.flatMap { it }.forEach { block ->
                val uv = FloatArray(4)
                val atlas: TextureAtlas.Atlas = blockTextureAtlas.getUv(block.type.id, uv)
                val texture: Texture = textureSideMap[atlas.atlasId]!!
                val pair = blockIndexModelMap[atlas.atlasId]
                //todo arrayOf可能是动态的
                if (pair == null) blockIndexModelMap[atlas.atlasId] =
                    texture to mutableListOf(BlockModel(block.x, block.y, BlockUv(arrayOf(uv))))
                else pair.second.add(BlockModel(block.x, block.y, BlockUv(arrayOf(uv))))
            }

            val blockAtlases: List<BlockAtlas> = blockIndexModelMap.toList()
                .map { (_, pair) ->
                    BlockAtlas(pair.first, pair.second, pair.second.size)
                }

            val size: Int = world.blocks.sumOf { blockLine -> blockLine.size }
            return WorldModel(world, blockAtlases, size, blockTextureAtlas)
        }
    }

    /**
     * # 纹理&方块集，为了应对不同大小的纹理
     *
     * @param texture 纹理大图
     * @param blockModel 方块模型列表
     * @param size 方块数量（实例数量）
     */
    class BlockAtlas(val texture: Texture, val blockModel: List<BlockModel>, val size: Int)
}