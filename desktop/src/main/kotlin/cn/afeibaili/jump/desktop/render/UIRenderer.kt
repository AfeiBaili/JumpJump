package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.LayoutRenderer
import cn.afeibaili.gl.render.RectangleRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.layout.fixed.fixed
import cn.afeibaili.gl.render.layout.shape.rectangle
import cn.afeibaili.gl.render.layout.weigth.columnWeight
import cn.afeibaili.gl.render.layout.weigth.rowWeight
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
        rowWeight {
            columnWeight {
                rectangle("rect2").setting {
                    it.setWeight(1f).setOffsetX(0f)
                }
                rectangle("rect3").setting {
                    it.setWeight(1f).setOffsetY(20f)
                }
            }.setting {
                it.setWeight(1f)
            }

            rectangle("rect1").setting {
                it.setWeight(1f).setOffsetX(0f)
            }
        }.setting { it.setSize(400f, 400f) }
    }.layout {
        fixed {
            rectangle("rect4").setting { it.fillMaxSize() }
        }.setting { it.setSize(400f, 400f).setOffsetY(400f) }
    }

    fun init() {
        layout()
        logger.info("loaded layout")
        val vertexShader = Shader.create(
            Shader.ShaderType.VERTEX,
            ResourceFileGetter.getResourceFile("shader/rectangle.vert").readText()
        )
        val fragmentShader = Shader.create(
            Shader.ShaderType.FRAGMENT,
            ResourceFileGetter.getResourceFile("shader/rectangle.frag").readText()
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