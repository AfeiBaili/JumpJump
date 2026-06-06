package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.GridRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.map.MapManager
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.createLogger
import cn.afeibaili.jump.desktop.world.World


/**
 * # 世界渲染器
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:59
 */

class WorldRenderer {
    private val logger = createLogger { "WorldRenderer" }

    val world get() = _world
    val camera get() = _camera
    val program get() = _program
    val gridRenderer get() = _gridRenderer

    private lateinit var _world: World
    private lateinit var _camera: Camera
    private lateinit var _program: Program
    private lateinit var _gridRenderer: GridRenderer

    fun init() {
        MapManager.load()
        logger.info("upload texture to gpu")
        World.blocksTexture.atlas.forEach { it.texture.upload() }
        logger.info("transform map to world")
        _world = World.of(MapManager.maps[0])
        _camera = Camera("projection", "view")
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
        _program.link()
        _gridRenderer = GridRenderer(_program, _camera)
    }

    fun render() {
        _camera.apply(_program)
        _world.atlas.atlas[0].texture.bind()
        _gridRenderer.renderGrid(
            {
                for (blockLine in _world.blocks) {
                    for (block in blockLine) {
                        putFloat(block.tile.x.toFloat())
                        putFloat(block.tile.y.toFloat())
                    }
                }
            },
            {
                for (blocks in _world.blocks) {
                    for (block in blocks) {
                        putFloat(block.uv[0])
                        putFloat(block.uv[1])
                        putFloat(block.uv[2])
                        putFloat(block.uv[3])
                    }
                }
            },
            _world.size
        )
    }
}