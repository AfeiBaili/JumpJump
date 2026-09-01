package cn.afeibaili.jump.desktop.world.model

import cn.afeibaili.gl.exception.ImageException
import cn.afeibaili.gl.image.Atlas
import cn.afeibaili.gl.image.Texture
import cn.afeibaili.gl.util.Index
import cn.afeibaili.jump.common.block.BlockTypes
import cn.afeibaili.jump.common.json.BlockInfo
import cn.afeibaili.jump.common.resource.BlockInfoLoader
import cn.afeibaili.jump.common.world.Chunk
import cn.afeibaili.jump.desktop.render.texture.TextureManager
import cn.afeibaili.jump.desktop.world.block.BlockModel
import cn.afeibaili.jump.desktop.world.block.BlockModelType
import cn.afeibaili.jump.desktop.world.block.BlockUv


/**
 * # 区块模型
 *
 * @author AfeiBaili
 * @version 2026/8/31 13:20
 */

class ChunkModel(val chunk: Chunk, val blockAtlas: List<BlockAtlas>) {
    var changed = true

    //todo 细化uv和实例
    fun update() {
        blockAtlas.forEach { blockAtlas ->
            blockAtlas.updateUvBuffer()
            blockAtlas.updateInstanceBuffer()
        }
        changed = false
    }

    fun setBlockAt() {
        changed = true
        TODO()
    }

    companion object {
        val blockTextureAtlas get() = TextureManager.blockTextureAtlas
        val textureSide get() = TextureManager.textureSizeMap
        val blockInfo get() = BlockInfoLoader.load()

        fun of(chunk: Chunk): ChunkModel {
            val blockTypeModelMap = mutableMapOf<String, BlockModelType>()
            val blockModelData = mutableMapOf<Index, BlockTextureModelList>()

            chunk.blocks.forEach { block ->
                var atlas: Atlas? = blockTextureAtlas.getAtlas(block.id)
                if (atlas == null) {
                    atlas = blockTextureAtlas.getAtlas(BlockTypes.ERROR.id)
                }
                atlas ?: throw ImageException("找不到错误纹理，其中纹理缺失: ${block.type.id}")

                val uvs: List<FloatArray> = runCatching {
                    blockTextureAtlas.getUvs(block.id)
                }.getOrElse {
                    runCatching {
                        blockTextureAtlas.getUvs(BlockTypes.ERROR.id)
                    }.getOrElse { throw ImageException("找不到错误纹理uv") }
                }

                var blockModelType: BlockModelType? = blockTypeModelMap[block.id]
                if (blockModelType == null) {
                    val info: BlockInfo? = blockInfo[block.id]
                    val switchIntervalMilli: Int = info?.switchIntervalMilli ?: 500
                    blockTypeModelMap[block.id] = BlockModelType.register(
                        block.type.identifier,
                        BlockUv(uvs, switchIntervalMilli)
                    )
                }
                blockModelType = blockTypeModelMap[block.id]!!

                val texture: Texture = textureSide[atlas.atlasId]!!
                val modelArray: BlockTextureModelList? = blockModelData[atlas.atlasId]
                val model = BlockModel(block.x, block.y, blockModelType)
                if (modelArray == null) blockModelData[atlas.atlasId] =
                    BlockTextureModelList(texture, mutableListOf(model))
                else modelArray.blocks.add(model)
            }

            val atlases: List<BlockAtlas> =
                blockModelData.map { (_, bl) -> BlockAtlas(bl.texture, bl.blocks, bl.blocks.size) }

            return ChunkModel(chunk, atlases)
        }
    }

    class BlockTextureModelList(val texture: Texture, val blocks: MutableList<BlockModel>)
}