package cn.afeibaili.jump.desktop.block


/**
 * # 存放方块纹理和UV信息
 *
 * @author AfeiBaili
 * @version 2026/6/30 12:32
 */

class BlockUv(val uv: List<FloatArray>) {
    var indexUv = 0

    fun getNext(): FloatArray {
        if (++indexUv >= uv.size) {
            indexUv = 0
        }
        return get()
    }

    fun get(): FloatArray = uv[indexUv]
}