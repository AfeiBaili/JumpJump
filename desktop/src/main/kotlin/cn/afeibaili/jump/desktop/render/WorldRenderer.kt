package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.render.WorldRenderer
import cn.afeibaili.gl.render.camera.Camera
import cn.afeibaili.gl.render.shader.Program
import cn.afeibaili.gl.render.shader.Shader
import cn.afeibaili.jump.common.resource.ResourceFileGetter
import cn.afeibaili.jump.common.util.logger
import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.render.texture.TextureManager
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
        TextureManager.blockTextureAtlas.atlas.forEach { (_, atlas) -> atlas.texture.upload() }
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
        renderer.render()
    }

    companion object {
        class WorldRender(
            override val program: Program,
            override val camera: Camera,
            val world: WorldModel,
        ) : WorldRenderer(program, camera) {
            fun render() {
                world.layers.forEach { layer ->
                    layer.chunks.forEach { chunkModel ->
                        chunkModel.update()

                        for (atlas in chunkModel.blockAtlas) {
                            atlas.texture.bind()
                            uploadInstanceBuffer(atlas.instanceBuffer)
                            uploadUvBuffer(atlas.uvBuffer)
                            renderInstance(atlas.size)
                        }
                    }
                }
            }
        }
    }
}