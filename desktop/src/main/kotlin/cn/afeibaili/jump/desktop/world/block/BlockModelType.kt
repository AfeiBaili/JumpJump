package cn.afeibaili.jump.desktop.world.block

import cn.afeibaili.jump.common.Identifier


/**
 * # 方块类型
 *
 * @author AfeiBaili
 * @version 2026/7/3 00:30
 */

class BlockModelType(val identifier: Identifier, val uv: BlockUv) {
    companion object {
        val all = mutableListOf<BlockModelType>()
        fun register(identifier: Identifier, uv: BlockUv): BlockModelType {

            return BlockModelType(identifier, uv).also { all.add(it) }
        }
    }
}