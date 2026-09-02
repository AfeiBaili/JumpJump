package cn.afeibaili.jump.desktop.world

import cn.afeibaili.gl.input.Key
import cn.afeibaili.gl.input.MouseButton
import cn.afeibaili.jump.common.block.Block
import cn.afeibaili.jump.common.block.BlockType
import cn.afeibaili.jump.common.identifier
import cn.afeibaili.jump.common.world.World
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.entity.Player
import cn.afeibaili.jump.desktop.input.KeySet
import cn.afeibaili.jump.desktop.input.MouseButtonSet
import cn.afeibaili.jump.desktop.logic.TickHandler
import org.lwjgl.glfw.GLFW
import kotlin.math.floor


/**
 * # 地图修改器
 *
 * @author AfeiBaili
 * @version 2026/8/27 20:23
 */

class WorldEditor(val world: World) : TickHandler {
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
    var maxLayerIndex = world.layers.size
    var currentLayerIndex = 0

    // input /////
    val mouseButtonSet = MouseButtonSet("mouse" identifier "world.editor")
    val keySet = KeySet("key" identifier "world.editor")

    init {
        TickHandler.addHandler(this)
        loadInput()
        mouseButtonSet.on()
        keySet.on()
    }

    override fun tick() {
        blockPositionX = getCurrentBlockX()
        blockPositionY = getCurrentBlockY()
        currentCursorBlock = world.getBlockAt(0, blockPositionX, blockPositionY)
    }

    fun switchNextLayer() {
        if (currentLayerIndex < maxLayerIndex - 1) currentLayerIndex++
        else currentLayerIndex = 0
    }

    fun switchLastLayer() {
        if (currentLayerIndex <= 0) currentLayerIndex = world.layers.size - 1
        else currentLayerIndex--
    }

    fun loadInput() {
        mouseButtonSet.bind(MouseButton("place.block", GLFW.GLFW_MOUSE_BUTTON_2)) {
            released { placeBlockByButton() }
        }

        keySet.bind(Key("switch.last.layer", GLFW.GLFW_KEY_MINUS)) {
            released {
                switchLastLayer()
            }
        }
        keySet.bind(Key("switch.next.layer", GLFW.GLFW_KEY_EQUAL)) {
            released {
                switchNextLayer()
            }
        }
    }

    fun placeBlockByButton() {
        world.setBlockAt(currentLayerIndex, getCurrentBlockX(), getCurrentBlockY(), BlockType.STONE)
    }

    fun getCurrentBlockX() = floor(currentPlayerX + (currentCursorX.toFloat() / width) * zoom * aspect).toInt()
    fun getCurrentBlockY() = floor(currentPlayerY + ((height - currentCursorY.toFloat()) / height) * zoom).toInt()
}