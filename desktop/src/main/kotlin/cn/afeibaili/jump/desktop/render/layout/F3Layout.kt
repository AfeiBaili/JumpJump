package cn.afeibaili.jump.desktop.render.layout

import cn.afeibaili.gl.font.FontFactory
import cn.afeibaili.gl.input.Key
import cn.afeibaili.gl.render.Color
import cn.afeibaili.gl.render.layout.Layout
import cn.afeibaili.gl.render.layout.adapt.rowAdapt
import cn.afeibaili.gl.render.layout.align.AlignmentSetting
import cn.afeibaili.gl.render.layout.align.AlignmentType
import cn.afeibaili.gl.render.layout.align.block
import cn.afeibaili.gl.render.layout.text.TextUpdater
import cn.afeibaili.gl.render.layout.text.text
import cn.afeibaili.jump.common.identifier
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.input.KeySet
import org.lwjgl.glfw.GLFW

/**
 * # 布局
 *
 * @author AfeiBaili
 * @version 2026/8/26 14:04
 */
class F3Layout {
    val font = FontFactory.create(
        "source", ResourceFileGetter.getResourceFile("font/SourceHanSansHWSC-Regular.otf").canonicalPath, 64
    ).apply { texture.upload() }
    val textUpdater = TextUpdater()
    val window = Application.window
    val textBackgroundColor = Color.parse("#2B2D3080")
    val keySet = KeySet("key" identifier "f3")

    lateinit var f3: Layout

    fun load() {
        loadKey()
        keySet.onKey()
        layout()
    }

    private fun loadKey() {
        keySet.bind(Key("open_f3", GLFW.GLFW_KEY_F3)) {
            keyClick {
                f3.showable = !f3.showable
                Application.rendererSystem.uiRenderer.update()
            }
        }
    }

    private fun layout() = Application.screen.layout {
        f3 = block(setting = { it.maxSize() }) {
            rowAdapt(setting = { it: AlignmentSetting -> it.align(AlignmentType.LEFT_TOP) }) {
                text(
                    "debug.greet", "press f3 open debug", font, updater = textUpdater, scale = 0.5f, x = 10f, y = 10f,
                    backgroundColor = textBackgroundColor,
                )
                text(
                    "debug.fps", "init fps", font, updater = textUpdater, scale = 0.5f, x = 10f, y = 10f,
                    backgroundColor = textBackgroundColor,
                )
                text(
                    "debug.v-sync", "init v-sync", font, updater = textUpdater, scale = 0.5f, x = 10f, y = 10f,
                    backgroundColor = textBackgroundColor,
                )
            }
        }
    }

    fun updateText() {
        textUpdater.update("debug.fps", "fps: ${Application.rendererSystem.fps()}")
        textUpdater.update("debug.v-sync", "v-sync: ${window.vsync}")
    }
}