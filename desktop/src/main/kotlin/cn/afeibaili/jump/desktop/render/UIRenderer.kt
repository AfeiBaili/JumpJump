package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.LayoutRenderer
import cn.afeibaili.gl.render.RectangleRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.layout.Setting
import cn.afeibaili.gl.render.layout.align.block
import cn.afeibaili.gl.render.layout.shape.rectangle
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.desktop.Application

/**
 * # 界面渲染器
 *
 * @author AfeiBaili
 * @version 2026/8/16 13:34
 */

class UIRenderer {
    private val logger = logger { "UIRenderer" }
    private val window get() = Application.window
    lateinit var layoutRenderer: LayoutRenderer

    fun layout() = Application.screen.layout {
        block(setting = Setting().size(this)) {
            rectangle(setting = Setting().size(400f, 400f), key = "")
        }
    }

    fun init() {
        layout()
        logger.info("loaded layout")
        val vertexShader = Shader.create(
            Shader.ShaderType.VERTEX, ResourceFileGetter.getResourceFile("shader/rectangle.vert").readText()
        )
        val fragmentShader = Shader.create(
            Shader.ShaderType.FRAGMENT, ResourceFileGetter.getResourceFile("shader/rectangle.frag").readText()
        )
        val program: Program = Program.create(vertexShader, fragmentShader).apply { link() }
        val camera = Camera(program).apply { ortho(0f, window.width.toFloat(), window.height.toFloat(), 0f, -1f, 1f) }
        layoutRenderer = LayoutRenderer(RectangleRenderer(program, camera), Application.screen)
        layoutRenderer.update()
    }

    fun render() {
        layoutRenderer.render()
    }
}