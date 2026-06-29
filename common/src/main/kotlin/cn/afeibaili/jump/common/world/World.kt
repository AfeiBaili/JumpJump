package cn.afeibaili.jump.common.world

import cn.afeibaili.jump.common.block.Block


/**
 * # 世界类，一个世界一个关卡
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:33
 */

class World(val name: String, val blocks: List<List<Block>>) {
    override fun toString(): String {
        return buildString {
            appendLine("world name: $name")
            blocks.forEach {
                appendLine("|--tile ${it.joinToString("")}")
            }
        }
    }
}