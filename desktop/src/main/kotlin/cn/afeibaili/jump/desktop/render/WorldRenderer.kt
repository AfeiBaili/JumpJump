package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.GridRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.common.world.WorldManager
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.world.WorldModel


/**
 * # 世界渲染器
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:59
 */

class WorldRenderer : Renderable {
    private val logger = createLogger { "WorldRenderer" }

    val camera get() = _camera
    val program get() = _program
    val gridRenderer get() = _gridRenderer
    val world get() = Application.world

    private lateinit var _camera: Camera
    private lateinit var _program: Program
    private lateinit var _gridRenderer: GridRenderer

    fun init() {
        logger.info("initialize world")
        WorldManager.load()
        logger.info("upload texture to gpu")
        WorldModel.blockTextureAtlas.atlas.forEach { (_, atlas) -> atlas.texture.upload() }
        logger.info("transform to world model")
        logger.info("create program")
        _program = Program.create(
            Shader.create(
                Shader.ShaderType.VERTEX,
                ResourceFileGetter.getResourceFile("shader/world.vert").readText()
            ),
            Shader.create(
                Shader.ShaderType.FRAGMENT,
                ResourceFileGetter.getResourceFile("shader/world.frag").readText()
            )
        )
        _camera = Camera(_program, "projection", "view")
        _program.link()
        _gridRenderer = GridRenderer(_program, _camera)
    }

    override fun render() {
        _camera.apply()

        world.blockModels.forEach { blockModel ->
            blockModel.texture.bind()

            _gridRenderer.renderGrid(
                {
                    for (block in blockModel.blockModel) {
                        putFloat(block.x.toFloat())
                        putFloat(block.y.toFloat())
                    }
                },
                {
                    for (block in blockModel.blockModel) {
                        val uv = block.blockUv.get()
                        putFloat(uv[0])
                        putFloat(uv[1])
                        putFloat(uv[2])
                        putFloat(uv[3])
                    }
                },
                blockModel.size
            )
        }
    }
}