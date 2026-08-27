package cn.afeibaili.jump.desktop.render.layout

import cn.afeibaili.gl.font.FontFactory
import cn.afeibaili.gl.render.Color
import cn.afeibaili.gl.render.layout.adapt.rowAdapt
import cn.afeibaili.gl.render.layout.align.AlignmentSetting
import cn.afeibaili.gl.render.layout.align.AlignmentType
import cn.afeibaili.gl.render.layout.align.block
import cn.afeibaili.gl.render.layout.text.TextUpdater
import cn.afeibaili.gl.render.layout.text.text
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.desktop.Application

/**
 * # 布局
 *
 * @author AfeiBaili
 * @version 2026/8/26 14:04
 */
class Layout {
    val font = FontFactory.create(
        "source", ResourceFileGetter.getResourceFile("font/SourceHanSansHWSC-Regular.otf").canonicalPath, 64
    ).apply { texture.upload() }
    val textUpdater = TextUpdater()
    val window = Application.window
    val textBackgroundColor = Color.parse("#2B2D3080")

    fun layout() = Application.screen.layout {
        block(setting = { it.maxSize() }) {
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