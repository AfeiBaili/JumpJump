package cn.afeibaili.jump.desktop.world.model

import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.common.world.Layer
import cn.afeibaili.jump.common.world.World


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
        private val logger = logger { "WorldModel" }

        fun of(world: World): WorldModel {
            val models: Array<LayerModel> = Array(world.layers.size) { i ->
                val layer: Layer = world.layers[i]
                logger.info("transform layer: ${layer.name}")
                LayerModel.of(layer)
            }
            return WorldModel(world, models)
        }
    }
}