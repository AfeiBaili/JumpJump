package cn.afeibaili.jump.desktop.render

import cn.afeibaili.jump.desktop.block.BlockModel


/**
 * # 更新动态纹理逻辑类
 *
 * @author AfeiBaili
 * @version 2026/7/2 20:57
 */

class BlockRenderer {
    val block get() = BlockModel.all

    fun init() {

    }

    fun update() {
        block.forEach { it.blockUv.update() }
    }
}