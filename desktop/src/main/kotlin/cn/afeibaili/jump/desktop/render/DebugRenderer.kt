package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.font.FontManager
import cn.afeibaili.gl.font.Text
import cn.afeibaili.gl.render.RectangleRenderer
import cn.afeibaili.gl.render.TextRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.layout.UnknownLayout
import cn.afeibaili.gl.render.layout.shape.rectangle
import cn.afeibaili.gl.render.layout.weigth.rowWeight
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.desktop.Application

/**
 * # 调试文字渲染
 *
 * @author AfeiBaili
 * @version 2026/8/1 03:31
 */

class DebugRenderer {
    val font = FontManager.create(
        "source", ResourceFileGetter.getResourceFile("font/SourceHanSansHWSC-Regular.otf").canonicalPath, 32
    )
    var textProgram: Program? = null
    var rectProgram: Program? = null
    var camera: Camera? = null
    var textRenderer: TextRenderer? = null
    var rectRenderer: RectangleRenderer? = null
    val window get() = Application.Companion.window

    fun init() {
        textProgram = Program.Companion.create(
            Shader.Companion.create(
                Shader.ShaderType.VERTEX, ResourceFileGetter.getResourceFile("shader/text.vert").readText()
            ), Shader.Companion.create(
                Shader.ShaderType.FRAGMENT, ResourceFileGetter.getResourceFile("shader/text.frag").readText()
            )
        )
        rectProgram = Program.Companion.create(
            Shader.Companion.create(
                Shader.ShaderType.VERTEX, ResourceFileGetter.getResourceFile("shader/rectangle.vert").readText()
            ), Shader.Companion.create(
                Shader.ShaderType.FRAGMENT, ResourceFileGetter.getResourceFile("shader/rectangle.frag").readText()
            )
        )
        textProgram!!.link()
        rectProgram!!.link()
        camera = Camera(textProgram!!)
        camera!!.ortho(0f, window.width.toFloat(), 0f, window.height.toFloat(), -1f, 1f)
        textRenderer = TextRenderer(font, textProgram!!, camera!!)
        rectRenderer = RectangleRenderer(rectProgram!!, camera!!)
        initLayout()
    }

    fun buildText() {
        textRenderer!!.update(Text("FPS: ${Application.Companion.rendererSystem.fps()}", 10f, 10f))
    }

    fun render() {
        buildText()
        textRenderer!!.render()
        rectRenderer!!.render()
    }

    fun initLayout() {
        val rowWeight: UnknownLayout = Application.screen.rowWeight {
            rectangle().setWeight(1f).setting {
                it.setOffsetX(0f).setOffsetY(5f)
            }
            rectangle().setWeight(1f).setting {
                it.setOffsetX(0f).setOffsetY(5f)
            }
        }

        rowWeight.items.forEach { it ->
            println("x = ${it.x}, y = ${it.y}, width = ${it.width}, height = ${it.height}")

            rectRenderer!!.put(it.toString(), it.x, it.y, it.width, it.height)
        }
    }
}
