package cn.afeibaili.jump.desktop.world.model

import cn.afeibaili.gl.exception.ImageException
import cn.afeibaili.gl.image.Atlas
import cn.afeibaili.gl.image.Texture
import cn.afeibaili.gl.util.Index
import cn.afeibaili.jump.common.Identifier
import cn.afeibaili.jump.common.block.BlockTypes
import cn.afeibaili.jump.common.json.BlockInfo
import cn.afeibaili.jump.common.resource.BlockInfoLoader
import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.common.world.Chunk
import cn.afeibaili.jump.common.world.Layer
import cn.afeibaili.jump.common.world.World
import cn.afeibaili.jump.desktop.render.texture.TextureManager
import cn.afeibaili.jump.desktop.world.block.BlockModel
import cn.afeibaili.jump.desktop.world.block.BlockModelType
import cn.afeibaili.jump.desktop.world.block.BlockUv


/**
 * # 世界模型，供渲染器使用
 *
 * 使用`WorldModel.of()`方法转换为世界模型
 *
 * @author AfeiBaili
 * @version 2026/8/29 13:06
 */

class WorldModel(val world: World, val layers: Array<LayerModel>) {
    companion object {
        val blockAtlas get() = TextureManager.blockTextureAtlas
        val textureSide get() = TextureManager.textureSideMap
        val blockInfo = BlockInfoLoader.load()
        val blockTypeMap = mutableMapOf<Identifier, BlockModelType>()
        val blockModelData = mutableMapOf<Index, BlockTextureModelList>()
        private val logger = logger { "WorldModel" }

        fun of(world: World): WorldModel {
            world.layers.forEach { layer ->
                layer.chunks.forEach { chunk ->
                    chunk.blocks.forEach { block ->
                        var atlas: Atlas? = blockAtlas.getAtlas(block.type.id)
                        if (atlas == null) {
                            logger.warn("在图集中找不到此id: ${block.type.id}")
                            atlas = blockAtlas.getAtlas(BlockTypes.ERROR.id)
                        }
                        atlas ?: throw ImageException("找不到错误纹理，其中纹理缺失: ${block.type.id}")


                        val uvs: List<FloatArray> = runCatching {
                            blockAtlas.getUvs(block.type.id)
                        }.getOrElse {
                            runCatching {
                                blockAtlas.getUvs(BlockTypes.ERROR.id)
                            }.getOrElse {
                                throw ImageException("找不到错误纹理uv")
                            }
                        }


                        var blockModelType: BlockModelType? = blockTypeMap[block.type.identifier]
                        if (blockModelType == null) {
                            val info: BlockInfo? = blockInfo[block.type.id]
                            val switchIntervalMilli: Int = info?.switchIntervalMilli ?: 500
                            blockTypeMap[block.type.identifier] = BlockModelType.register(
                                block.type.identifier, BlockUv(uvs, switchIntervalMilli)
                            )
                        }
                        blockModelType = blockTypeMap[block.type.identifier]

                        val texture: Texture = textureSide[atlas.atlasId]!!
                        val textureModelList: BlockTextureModelList? = blockModelData[atlas.atlasId]
                        val blockModel = BlockModel(
                            chunk.chunkX * Chunk.CHUNK_SIDE + block.x.toFloat(),
                            chunk.chunkY * Chunk.CHUNK_SIDE + block.y.toFloat(),
                            blockModelType!!
                        )
                        if (textureModelList == null) blockModelData[atlas.atlasId] =
                            BlockTextureModelList(texture, mutableListOf(blockModel))
                        else textureModelList.blocks.add(blockModel)
                    }
                }

            }

            val blockAtlases: List<BlockAtlas> =
                blockModelData.values.map { it -> BlockAtlas(it.texture, it.blocks, it.blocks.size) }

            val layerModels = Array(world.layers.size) { index ->
                val layer: Layer = world.layers[index]
                LayerModel(layer, blockAtlases)
            }

            return WorldModel(world, layerModels)
        }

        class BlockTextureModelList(val texture: Texture, val blocks: MutableList<BlockModel>)
    }
}