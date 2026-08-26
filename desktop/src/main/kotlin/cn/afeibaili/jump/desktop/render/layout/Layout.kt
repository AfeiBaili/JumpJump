package cn.afeibaili.jump.desktop.render.layout

import cn.afeibaili.gl.font.FontFactory
import cn.afeibaili.gl.render.Color
import cn.afeibaili.gl.render.layout.adapt.rowAdapt
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

    fun layout() = Application.screen.layout {
        block(setting = { it.maxSize() }) {
            rowAdapt {
                text(
                    "debug.fps",
                    "FPS: ${Application.rendererSystem.fps()}",
                    font,
                    updater = textUpdater,
                    scale = 2f,
                    color = Color.WHITE,
                    backgroundColor = Color.parse("#2B2D3080")
                )
                text(
                    "debug.hello",
                    "hello freetype",
                    font,
                    updater = textUpdater,
                    scale = 0.5f,
                    color = Color.WHITE,
                    backgroundColor = Color.parse("#2B2D3080")
                )
            }
        }
    }

    fun updateText() {
        textUpdater.update("debug.fps", "FPS: ${Application.rendererSystem.fps()}")
    }
}