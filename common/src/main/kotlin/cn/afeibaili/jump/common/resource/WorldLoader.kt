package cn.afeibaili.jump.common.resource

import cn.afeibaili.jump.common.exception.WorldIsEmptyException
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.common.world.World
import java.io.File
import java.io.FileNotFoundException

/**
 * # 世界加载器，加载外部世界
 *
 * 解析“.world”结尾的文件
 *
 *
 * @see WorldLoader
 * @see WorldParser
 * @author AfeiBaili
 * @version 2026/6/2 22:58
 */

object WorldLoader : Loader<List<World>> {
    val worldPath = "${System.getProperty("user.dir")}/world"
    private val logger = createLogger { "WorldLoader" }
    private val parser = WorldParser()

    override fun load(): List<World> {
        logger.info("world loading...")
        val worldFile: List<File> = getWorldFile()
        val worlds = mutableListOf<World>()
        worldFile.forEach { file ->
            val text: String = file.readText()
            logger.info("loading world file: ${file.name}")
            val world = parser.parse(text)
            worlds.add(world)
        }
        return worlds
    }

    fun getWorldFile(): List<File> {
        val worldPath = File(worldPath)
        if (!worldPath.exists()) throw FileNotFoundException("找不到地图文件夹")
        val worldFileList: List<File> = worldPath.listFiles().filter { it.name.endsWith(".world") }
        if (worldFileList.isEmpty()) throw WorldIsEmptyException("找不到地图文件")
        return worldFileList
    }
}