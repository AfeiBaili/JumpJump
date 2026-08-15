package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.font.FontManager
import cn.afeibaili.gl.font.Text
import cn.afeibaili.gl.render.RectangleRenderer
import cn.afeibaili.gl.render.TextRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.layout.UnknownLayout
import cn.afeibaili.gl.render.layout.shape.rectangle
import cn.afeibaili.gl.render.layout.weigth.columnWeight
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.desktop.Application

/**
 * # 调试文字渲染
 *
 * @author AfeiBaili
 * @version 2026/8/1 03:31
 */

class DebugRenderer {
    private val logger = logger { "DebugRenderer" }
    val font = FontManager.create(
        "source", ResourceFileGetter.getResourceFile("font/SourceHanSansHWSC-Regular.otf").canonicalPath, 32
    )
    lateinit var textProgram: Program
    lateinit var rectProgram: Program
    lateinit var textCamera: Camera
    lateinit var rectCamera: Camera
    lateinit var textRenderer: TextRenderer
    lateinit var rectRenderer: RectangleRenderer
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
        textProgram.link()
        rectProgram.link()
        textCamera = Camera(textProgram)
        rectCamera = Camera(rectProgram)
        textCamera.ortho(0f, window.width.toFloat(), 0f, window.height.toFloat(), -1f, 1f)
        rectCamera.ortho(0f, window.width.toFloat(), window.height.toFloat(), 0f, -1f, 1f)
        textRenderer = TextRenderer(font, textProgram, textCamera)
        rectRenderer = RectangleRenderer(rectProgram, rectCamera)
        initLayout()
    }

    fun buildText() {
        textRenderer.update(Text("FPS: ${Application.Companion.rendererSystem.fps()}", 10f, 10f))
    }

    fun render() {
        buildText()
        textRenderer.render()
        rectRenderer.render()
    }

    fun initLayout() {
        val rowWeight: UnknownLayout = Application.screen.columnWeight {
            rectangle().setWeight(1f).setting {
                it.setOffsetY(0f)
            }
            rectangle().setWeight(2f).setting {
                it.setOffsetY(20f)
            }
        }

        rowWeight.items.forEach { it ->
            logger.debug("x = ${it.absoluteY}, y = ${it.absoluteY}, width = ${it.width}, height = ${it.height}")
            rectRenderer.put(it.toString(), it.absoluteX, it.absoluteY, it.width, it.height)
        }
    }
}
