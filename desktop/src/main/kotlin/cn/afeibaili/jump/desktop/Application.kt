package cn.afeibaili.jump.desktop

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.jump.common.map.MapManager
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.world.World


/**
 * # 应用程序入口
 *
 *@author AfeiBaili
 *@version 2026/6/1 22:57
 */

class Application {
    companion object {
        val logger = createLogger { "Application" }

        val window: Window = Window.builder()
            .buildTitle("没有名字")
            .buildWidth(800)
            .buildHeight(800)
            .build()

        lateinit var world: World

        fun init() {
            Logger.printDebug = false
            Logger.writeFile = true
            logger.info("initializing worlds")
            MapManager.load()
            world = World.of(MapManager.maps[0])
        }

        @JvmStatic
        fun main(args: Array<String>) {
            init()
            logger.info("window is initialized")

            window.frameRender { print("") }

            stop()
            logger.info("windows is destroy")
        }

        fun stop() {
            window.close()
        }
    }
}