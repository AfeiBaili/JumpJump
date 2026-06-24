package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.process.ProcessThread
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
        private val logger = createLogger { "Application" } //日志器

        val window: Window = Window.builder() //窗口构建器
            .buildTitle("跳一跳").buildWidth(800).buildHeight(800).build()
        val windowSystem = WindowSystem(window) //窗口管理器
        val rendererSystem = RendererSystem() //渲染系统
        val processThread = ProcessThread()

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
            processThread.start()
            window.frameRender {
                rendererSystem.render()
            }
            processThread.thread.join()
            stop()
        }

        fun stop() {
            processThread.isActive = false
            window.close()
            logger.info("windows is destroy")
        }
    }
}