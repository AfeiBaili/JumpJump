package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.render.RendererSystem
import cn.afeibaili.jump.desktop.window.WindowSystem


/**
 * # 应用程序入口
 *
 *@author AfeiBaili
 *@version 2026/6/1 22:57
 */

class Application {
    companion object {
        private val logger = createLogger { "Application" }
        val rendererSystem = RendererSystem() //渲染系统

        val window: Window = Window.builder() //窗口构建器
            .buildTitle("没有名字").buildWidth(800).buildHeight(800).build()
        val windowSystem = WindowSystem(window) //窗口管理器

        fun init() {
            logger.info("setting logger")
            Logger.printDebug = false
            Logger.writeFile = true
            logger.info("initialize window system")
            windowSystem.init()
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