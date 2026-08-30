package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.WorldRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.world.model.WorldModel


/**
 * # 世界渲染器
 *
 * @author AfeiBaili
 * @version 2026/6/6 18:59
 */

class WorldRenderer {
    private val logger = logger { "WorldRenderer" }

    val camera get() = _camera
    val world get() = Application.world

    private lateinit var _camera: Camera
    private lateinit var _program: Program
    private lateinit var renderer: WorldRender

    fun init() {
        logger.info("upload texture to gpu")
        WorldModel.blockAtlas.atlas.forEach { (_, atlas) -> atlas.texture.upload() }
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
        renderer = WorldRender(_program, _camera, world)
    }

    fun render() {
        renderer.renderWorld()
    }

    companion object {
        class WorldRender(
            override val program: Program,
            override val camera: Camera,
            val world: WorldModel,
        ) : WorldRenderer(program, camera) {
            fun renderWorld() {
                world.layers.forEach { layerModel ->
                    layerModel.blockAtlas.forEach { blockAtlas ->
                        blockAtlas.texture.bind()
                        var instanceBuffer = getInstanceBuffer()
                        var uvBuffer = getUvBuffer()
                        if (instanceBuffer == null || uvBuffer == null) return
                        var count = 0
                        blockAtlas.blockModel.forEach { blockModel ->
                            if (count >= BLOCK_SIZE) {
                                putBuffer()
                                renderBatch(count)
                                instanceBuffer = getInstanceBuffer()
                                uvBuffer = getUvBuffer()
                                if (instanceBuffer == null || uvBuffer == null) return
                                count = 0
                            }

                            instanceBuffer!!.putFloat(blockModel.x)
                            instanceBuffer.putFloat(blockModel.y)

                            val uv = blockModel.type.uv.get()
                            uvBuffer!!.putFloat(uv[0])
                            uvBuffer.putFloat(uv[1])
                            uvBuffer.putFloat(uv[2])
                            uvBuffer.putFloat(uv[3])
                            count++
                        }

                        putBuffer()

                        if (count > 0) {
                            renderBatch(count)
                        }
                    }
                }
            }
        }
    }
}