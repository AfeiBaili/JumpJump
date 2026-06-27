package cn.afeibaili.jump.desktop.input

import cn.afeibaili.gl.input.Key
import cn.afeibaili.gl.input.KeyBind
import cn.afeibaili.jump.common.Identifier
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.logic.TickHandler

/**
 * # 一个按键集合，统一进行开关管理
 *
 * 已自动装载至KeySet.all集合中，无需手动装载
 *
 * ## 使用
 *
 * 创建`KeySet(identifier)`对象并使用绑定方法，进行按键绑定
 *
 * ```
 * ketSet.bind(Key("move_left", GLFW.GLFW_KEY_A)) {
 *     if (this.keyPress()) {
 *         xv += speed * LogicThread.self.step
 *     }
 * }
 * ```
 *
 * @author AfeiBaili
 * @version 2026/6/27 19:53
 */

data class KeySet(val identifier: Identifier) : TickHandler {
    companion object {
        @JvmStatic
        private val logger = createLogger { "KetSet" }
        val all = HashSet<KeySet>()
        fun addSet(keySet: KeySet) {
            all.add(keySet)
        }
    }

    init {
        TickHandler.addHandler(this)
        addSet(this)
    }

    var keyable: Boolean = false
    val set: MutableSet<Pair<KeyBind, KeyBind.() -> Unit>> = HashSet()

    override fun tick() {
        for (bind in set) {
            val (keybind, callback) = bind
            if (keyable) callback(keybind)
        }
    }

    fun bind(key: Key, callback: KeyBind.() -> Unit) {
        logger.info("registering key set: $identifier, ${key.id}")
        set.add(KeyBind(key, Application.window) to callback)
    }

    fun onKey() {
        keyable = true
    }

    fun offKey() {
        keyable = false

    }
}