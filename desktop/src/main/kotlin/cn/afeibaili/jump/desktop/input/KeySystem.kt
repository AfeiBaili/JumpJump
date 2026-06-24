package cn.afeibaili.jump.desktop.input

import cn.afeibaili.gl.Window
import cn.afeibaili.gl.input.KeyListener
import cn.afeibaili.gl.input.KeyTap


/**
 * # 按键系统
 *
 * @author AfeiBaili
 * @version 2026/6/23 20:55
 */

class KeySystem(val window: Window) {
    val keyTapMap = HashMap<String, KeyTap>()
    val keyMap = HashMap<String, KeyListener>()

}