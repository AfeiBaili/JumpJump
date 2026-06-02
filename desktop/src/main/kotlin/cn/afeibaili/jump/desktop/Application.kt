package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.jump.common.util.createLogger


/**
 * # 应用程序入口
 *
 *@author AfeiBaili
 *@version 2026/6/1 22:57
 */

class Application {
    companion object {
        val logger = createLogger { "Application" }

        @JvmStatic
        fun main(args: Array<String>) {
            val window: Window = Window.builder()
                .buildTitle("跳一跳")
                .buildWidth(800)
                .buildHeight(800)
                .build()
            logger.info("window is initialized")

            window.close()
            logger.info("windows is destroy")
        }
    }
}