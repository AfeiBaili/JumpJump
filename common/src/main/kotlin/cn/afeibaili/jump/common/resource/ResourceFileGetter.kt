package cn.afeibaili.jump.common.resource

import cn.afeibaili.jump.common.resource.Loader.Companion.userPath
import java.io.File
import java.io.FileNotFoundException


/**
 * # 获取图片资源
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:11
 */

object ResourceFileGetter {
    fun getResourceFileList(directoryName: String): List<File> {
        val file = File("$userPath/resource", directoryName)
        return file.listFiles().toList()
    }

    fun getResourceFile(fileName: String): File {
        val file = File("$userPath/resource", fileName)
        if (!file.exists()) throw FileNotFoundException("找不到文件: ${file.canonicalPath}")
        return file
    }
}