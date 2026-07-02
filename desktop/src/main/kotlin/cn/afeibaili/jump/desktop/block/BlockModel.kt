package cn.afeibaili.jump.desktop.block

/**
 * # 方块实例
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:43
 */

class BlockModel(val x: Int, val y: Int, val blockUv: BlockUv) {
    companion object {
        val all = mutableListOf<BlockModel>()

        operator fun plusAssign(blockModel: BlockModel) {
            all.add(blockModel)
        }
    }
}