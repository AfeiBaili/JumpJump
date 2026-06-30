package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.common.world.WorldManager
import cn.afeibaili.jump.desktop.entity.Player
import cn.afeibaili.jump.desktop.logic.LogicThread
import cn.afeibaili.jump.desktop.render.RendererSystem
import cn.afeibaili.jump.desktop.window.WindowSystem
import cn.afeibaili.jump.desktop.world.WorldModel


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
            .buildTitle("像素决斗")
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
        lateinit var world: WorldModel

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

        fun loadWorld(worldName: String): WorldModel {
            WorldManager.load()
            return WorldModel.of(
                WorldManager.worlds[worldName] ?: throw IllegalArgumentException(
                    "未知的世界i: $worldName; 当前世界列表${WorldManager.worlds.keys.joinToString("、")}"
                )
            )
        }

        @JvmStatic
        fun main(args: Array<String>) {
            world = loadWorld("test world")
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