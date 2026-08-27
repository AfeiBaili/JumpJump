package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.gl.render.layout.RootLayout
import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.common.world.WorldManager
import cn.afeibaili.jump.desktop.entity.Player
import cn.afeibaili.jump.desktop.logic.LogicThread
import cn.afeibaili.jump.desktop.render.RendererSystem
import cn.afeibaili.jump.desktop.window.WindowSystem
import cn.afeibaili.jump.desktop.world.WorldEditor
import cn.afeibaili.jump.desktop.world.WorldModel


/**
 * # 应用程序入口
 *
 *@author AfeiBaili
 *@version 2026/6/1 22:57
 */

class Application {
    companion object {
        var screenWidth = 800
        var screenHeight = 800

        init {
            Logger.printDebug = true
            Logger.writeFile = false
        }

        private val logger = logger { "Application" } //日志器
        var running = true
        val window: Window = Window.builder() //窗口构建器
            .buildTitle("像素决斗")
            .buildWidth(screenWidth)
            .buildHeight(screenHeight)
            .withVerticalSync(false)
            .withClearColor(0.1f, 0.1f, 0.1f, 1f)
            .build()
        val windowSystem = WindowSystem(window) //窗口管理器
        val screen = RootLayout(screenWidth.toFloat(), screenHeight.toFloat())
        val rendererSystem = RendererSystem() //渲染系统
        val logicThread = LogicThread()
        val camera get() = rendererSystem.worldRenderer.camera
        val player get() = Player.self
        val worldEditor = WorldEditor()
        lateinit var world: WorldModel

        fun setup() {
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
                    "未知的世界: $worldName; 当前世界列表${WorldManager.worlds.keys.joinToString("、")}"
                )
            )
        }

        @JvmStatic
        fun main(args: Array<String>) {
            world = loadWorld("test world")
            setup()
            logicThread.start()
            window.loopFrame({ running }) {
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