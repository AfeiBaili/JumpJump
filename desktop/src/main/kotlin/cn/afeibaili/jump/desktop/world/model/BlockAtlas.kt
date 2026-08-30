package cn.afeibaili.jump.desktop.world.model

import cn.afeibaili.gl.image.Texture
import cn.afeibaili.jump.desktop.world.block.BlockModel


/**
 * # 存储不同方块大小的图集
 *
 * @param texture 纹理大图
 * @param blockModel 方块模型列表
 * @param size 方块数量（实例数量）
 *
 * @author AfeiBaili
 * @version 2026/8/30 00:38
 */
class BlockAtlas(val texture: Texture, val blockModel: List<BlockModel>, val size: Int)