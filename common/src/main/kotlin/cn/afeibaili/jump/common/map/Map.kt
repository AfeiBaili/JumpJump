package cn.afeibaili.jump.common.map

import cn.afeibaili.jump.common.tile.Tile


/**
 * # 世界类，一个世界一个关卡
 *
 *@author AfeiBaili
 *@version 2026/6/2 22:33
 */

class Map(val name: String, val tiles: List<List<Tile>>) {
    override fun toString(): String {
        return buildString {
            appendLine("map name: $name")
            tiles.forEach {
                appendLine("|--tile ${it.joinToString("")}")
            }
        }
    }
}