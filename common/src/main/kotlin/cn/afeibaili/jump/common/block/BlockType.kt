package cn.afeibaili.jump.common.block

import cn.afeibaili.jump.common.Identifier


/**
 * # 方块类
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:33
 */

data class BlockType(val identifier: Identifier) {
    val id get() = identifier.id
}