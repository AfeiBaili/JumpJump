package cn.afeibaili.jump.desktop.world

import cn.afeibaili.gl.image.TextureAtlas
import cn.afeibaili.jump.common.map.Map
import cn.afeibaili.jump.common.resource.ResourceFileGetter


/**
 * # 世界、关卡实例
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:43
 */

class World(val map: Map, val blocks: List<List<Block>>) {
    companion object {
        val blocksTexture =
            TextureAtlas.create("block", ResourceFileGetter.get("tile"))

        fun of(map: Map): World {
            val blocks = ArrayList<ArrayList<Block>>()

            map.tiles.forEach { tileLine ->
                val blockLine = ArrayList<Block>()
                tileLine.forEach { tile ->
                    val uv = FloatArray(4)
                    blocksTexture.getUv(tile.type.identifier.id, uv)
                    blockLine.add(Block(tile, uv))
                }
                blocks.add(blockLine)
            }
            return World(map, blocks)
        }
    }
}