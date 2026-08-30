package cn.afeibaili.jump.desktop.world.model

import cn.afeibaili.jump.common.world.Layer


/**
 * # 世界层次模型
 *
 * 存储当前层次的方块数据
 *
 * @author AfeiBaili
 * @version 2026/8/30 00:02
 */

class LayerModel(val layer: Layer, val blockAtlas: List<BlockAtlas>)