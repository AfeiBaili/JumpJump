package cn.afeibaili.jump.desktop.render

import cn.afeibaili.gl.image.TextureAtlas
import cn.afeibaili.jump.common.resource.ResourceFileGetter


/**
 * 纹理管理器
 *
 * @author AfeiBaili
 * @version 2026/6/29 21:54
 */

object TextureManager {
    val blockTextureAtlas = TextureAtlas.create("block",ResourceFileGetter.getResourceFileList("tile"))

}