package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window


/**
 * # 应用程序入口
 *
 *@author AfeiBaili
 *@version 2026/6/1 22:57
 */

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val window: Window = Window.builder().buildWidth(800).build()

            window.toString()

            window.frameRender {
                println(111)
            }

            window.close()
        }
    }
}