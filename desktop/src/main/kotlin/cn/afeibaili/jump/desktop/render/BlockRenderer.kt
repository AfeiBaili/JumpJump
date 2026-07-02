package cn.afeibaili.jump.desktop.render

import cn.afeibaili.jump.desktop.block.BlockModelType


/**
 * # 更新动态纹理逻辑类
 *
 * @author AfeiBaili
 * @version 2026/7/2 20:57
 */

class BlockRenderer {
    val blockType get() = BlockModelType.all

    fun init() {

    }

    fun update() {
        blockType.forEach { it.uv.update() }
    }
}