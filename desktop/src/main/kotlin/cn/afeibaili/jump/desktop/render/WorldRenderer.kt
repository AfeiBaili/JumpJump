package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.GridRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.world.WorldManager
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.world.WorldModel


/**
 * # 世界渲染器
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:59
 */

class WorldRenderer : Renderable {
    private val logger = createLogger { "WorldRenderer" }

    val world get() = _worldModel
    val camera get() = _camera
    val program get() = _program
    val gridRenderer get() = _gridRenderer

    private lateinit var _worldModel: WorldModel
    private lateinit var _camera: Camera
    private lateinit var _program: Program
    private lateinit var _gridRenderer: GridRenderer

    fun init() {
        logger.info("initialize world")
        WorldManager.load()
        logger.info("upload texture to gpu")
        WorldModel.blocksTexture.atlas.forEach { it.texture.upload() }
        logger.info("transform to world model")
        _worldModel = WorldModel.of(WorldManager.worlds[0])
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
        _worldModel.atlas.atlas[0].texture.bind()
        _gridRenderer.renderGrid(
            {
                for (blockLine in _worldModel.blockModels) {
                    for (block in blockLine) {
                        putFloat(block.block.x.toFloat())
                        putFloat(block.block.y.toFloat())
                    }
                }
            },
            {
                for (blocks in _worldModel.blockModels) {
                    for (block in blocks) {
                        putFloat(block.uv[0])
                        putFloat(block.uv[1])
                        putFloat(block.uv[2])
                        putFloat(block.uv[3])
                    }
                }
            },
            _worldModel.size
        )
    }
}