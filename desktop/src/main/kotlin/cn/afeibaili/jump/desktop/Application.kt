package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.entity.Player
import cn.afeibaili.jump.desktop.logic.LogicThread
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

        var running = true
        val window: Window = Window.builder() //窗口构建器
            .buildTitle("跳一跳")
            .buildWidth(800)
            .buildHeight(800)
            .withVerticalSync(false)
            .withClearColor(0.1f, 0.1f, 0.1f, 1f)
            .build()
        val windowSystem = WindowSystem(window) //窗口管理器
        val rendererSystem = RendererSystem() //渲染系统
        val logicThread = LogicThread()
        val camera get() = rendererSystem.worldRenderer.camera
        val player get() = Player.self

        fun setup() {
            logger.info("setting logger")
            Logger.printDebug = false
            Logger.writeFile = true
            logger.info("initialize window system")
            windowSystem.init()
            logger.info("initialize renderer system")
            rendererSystem.init()
            logger.info("initialize player")
            player.init()

            logger.info("application is initialized")
        }

        @JvmStatic
        fun main(args: Array<String>) {
            setup()
            logicThread.start()
            window.frame({ running }) {
                rendererSystem.frame()
            }
            logicThread.thread.join()
        }

        fun stop() {
            running = false
            logicThread.stop()
            window.close()
            logger.info("windows is destroy")
        }
    }
}