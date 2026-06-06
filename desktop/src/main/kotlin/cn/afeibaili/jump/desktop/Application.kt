package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.render.RendererSystem


/**
 * # 应用程序入口
 *
 *@author AfeiBaili
 *@version 2026/6/1 22:57
 */

class Application {
    companion object {
        private val logger = createLogger { "Application" }
        val rendererSystem = RendererSystem()

        val window: Window = Window.builder()
            .buildTitle("没有名字")
            .buildWidth(800)
            .buildHeight(800)
            .build()


        fun init() {
            logger.info("setting logger")
            Logger.printDebug = false
            Logger.writeFile = true
            logger.info("initialize renderer system")
            rendererSystem.init()

            logger.info("window is initialized")
        }

        @JvmStatic
        fun main(args: Array<String>) {
            init()
            window.frameRender {
                rendererSystem.render()
            }
            stop()
        }

        fun stop() {
            window.close()
            logger.info("windows is destroy")
        }
    }
}