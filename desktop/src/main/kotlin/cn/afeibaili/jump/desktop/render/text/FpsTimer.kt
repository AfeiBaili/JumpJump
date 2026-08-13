package cn.afeibaili.jump.desktop.render.text

import cn.afeibaili.gl.util.Time

/**
 * # 用于计算FPS
 *
 * @author AfeiBaili
 * @version 2026/8/3 12:58
 */

class FpsTimer() {
    var fps: Int = 0
    private var _fps: Int = 0
    private var lastTime = Time.millis()
    private var accumulator = 0f

    operator fun invoke() = fps

    fun update() {
        val currentTime = Time.millis()
        val delta = currentTime - lastTime
        lastTime = currentTime
        accumulator += delta
        if (accumulator >= 1000) {
            accumulator -= 1000
            fps = _fps
            _fps = 0
        }
        _fps++
    }
}