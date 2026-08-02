package cn.afeibaili.jump.desktop.render.text

import cn.afeibaili.gl.font.FontManager
import cn.afeibaili.gl.font.Text
import cn.afeibaili.gl.render.TextRenderer
import cn.afeibaili.gl.render.camera.Camera
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
        "source",
        ResourceFileGetter.getResourceFile("font/SourceHanSansHWSC-Regular.otf").canonicalPath,
        64
    )
    var program: Program? = null
    var camera: Camera? = null
    var textRenderer: TextRenderer? = null
    val window get() = Application.window

    fun init() {
        program = Program.create(
            Shader.create(Shader.ShaderType.VERTEX, ResourceFileGetter.getResourceFile("shader/text.vert").readText()),
            Shader.create(Shader.ShaderType.FRAGMENT, ResourceFileGetter.getResourceFile("shader/text.frag").readText())
        )
        program!!.link()
        camera = Camera(program!!)
        camera!!.ortho(0f, window.width.toFloat(), 0f, window.height.toFloat(), -1f, 1f)
        textRenderer = TextRenderer(font, program!!, camera!!, window)
    }

    fun buildText() {
        textRenderer!!.update(Text("Hello Text", 10f, 10f))
        textRenderer!!.update(Text("Hello World", 10f, 64f))
    }

    fun render() {
        buildText()
        textRenderer!!.render()
    }
}