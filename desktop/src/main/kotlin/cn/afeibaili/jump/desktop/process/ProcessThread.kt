package cn.afeibaili.jump.desktop.process

import cn.afeibaili.jump.desktop.Application.Companion.window
import cn.afeibaili.jump.desktop.input.KeySystem


/**
 * # 更新处理线程
 *
 * @author AfeiBaili
 * @version 2026/6/23 23:09
 */

class ProcessThread {
    var isActive: Boolean = true

    val keySystem = KeySystem(window)

    val thread = Thread {
        while (isActive) {
            update()
        }
    }

    fun update() {

    }

    fun start() {
        thread.start()
    }

    fun stop() {
        isActive = false
    }
}