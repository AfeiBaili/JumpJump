package cn.afeibaili.jump.desktop.world.block

import cn.afeibaili.gl.tool.Time


/**
 * # 存放方块纹理和UV信息
 *
 * @author AfeiBaili
 * @version 2026/6/30 12:32
 */

class BlockUv(val uv: List<FloatArray>, var switchIntervalMilli: Int = 500) {
    var indexUv = 0
    var lastMillis = Time.millis()
    var accumulator = 0f

    fun getNext(): FloatArray {
        if (++indexUv >= uv.size) {
            indexUv = 0
        }
        return get()
    }

    fun get(): FloatArray = uv[indexUv]

    fun update() {
        val currentMillis = Time.millis()
        val delta = currentMillis - lastMillis
        lastMillis = currentMillis
        accumulator += delta
        if (accumulator > switchIntervalMilli) {
            accumulator -= switchIntervalMilli
            getNext()
        }
    }
}