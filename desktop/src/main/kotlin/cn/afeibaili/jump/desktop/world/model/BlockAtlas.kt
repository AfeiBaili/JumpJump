package cn.afeibaili.jump.desktop.world.model

import cn.afeibaili.gl.image.Texture
import cn.afeibaili.gl.render.WorldRenderer
import cn.afeibaili.jump.desktop.world.block.BlockModel
import org.lwjgl.BufferUtils


/**
 * # 存储不同方块大小的图集
 *
 * @param texture 纹理大图
 * @param blockModel 方块模型列表
 * @param size 方块数量（实例数量）
 *
 * @author AfeiBaili
 * @version 2026/8/30 00:38
 */
class BlockAtlas(val texture: Texture, val blockModel: List<BlockModel>, val size: Int) {
    val instanceBuffer = BufferUtils.createByteBuffer(WorldRenderer.INSTANCE_SIZE_BYTE.toInt())
    val uvBuffer = BufferUtils.createByteBuffer(WorldRenderer.UV_SIZE_BYTE.toInt())

    fun updateInstanceBuffer() {
        instanceBuffer.clear()
        blockModel.forEach { blockModel ->
            instanceBuffer.putInt(blockModel.x)
            instanceBuffer.putInt(blockModel.y)
        }
        instanceBuffer.flip()
    }

    fun updateUvBuffer() {
        uvBuffer.clear()
        blockModel.forEach { blockModel ->
            val uvs: FloatArray = blockModel.type.uv.get()
            uvBuffer.putFloat(uvs[0])
            uvBuffer.putFloat(uvs[1])
            uvBuffer.putFloat(uvs[2])
            uvBuffer.putFloat(uvs[3])
        }
        uvBuffer.flip()
    }
}