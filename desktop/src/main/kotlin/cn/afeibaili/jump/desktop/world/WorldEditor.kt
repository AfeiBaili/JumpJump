package cn.afeibaili.jump.desktop.world

import cn.afeibaili.jump.common.block.Block
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.entity.Player
import cn.afeibaili.jump.desktop.logic.TickHandler
import kotlin.math.floor


/**
 * # 地图修改器
 *
 * @author AfeiBaili
 * @version 2026/8/27 20:23
 */

class WorldEditor : TickHandler {
    val world get() = Application.world.world
    var currentCursorBlock: Block? = null
    val currentPlayerX get() = Player.self.x
    val currentPlayerY get() = Player.self.y
    val currentCursorX get() = Application.windowSystem.cursorX
    val currentCursorY get() = Application.windowSystem.cursorY
    val aspect get() = Application.windowSystem.aspect
    val width get() = Application.screenWidth
    val height get() = Application.screenHeight
    val zoom get() = Application.camera.zoom
    var blockPositionX = 0
    var blockPositionY = 0

    init {
        TickHandler.addHandler(this)
    }

    override fun tick() {
        val worldX = currentPlayerX + (currentCursorX.toFloat() / width) * zoom * aspect
        val worldY = currentPlayerY + ((height - currentCursorY.toFloat()) / height) * zoom

        val blockX = floor(worldX).toInt()
        val blockY = floor(worldY).toInt()

        blockPositionX = blockX
        blockPositionY = blockY
        currentCursorBlock = world.getBlockAt(0, blockX, -blockY)
    }
}