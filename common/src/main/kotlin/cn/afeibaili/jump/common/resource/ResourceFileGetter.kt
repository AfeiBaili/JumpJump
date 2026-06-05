package cn.afeibaili.jump.common.resource

import cn.afeibaili.jump.common.resource.Loader.Companion.userPath
import java.io.File


/**
 * # 获取图片资源
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:11
 */

object ResourceFileGetter : Getter<String, List<File>> {
    override fun get(param: String): List<File> {
        val file = File("$userPath/resource", param)
        return file.listFiles().filter { it.extension == "png" }.toList()
    }
}