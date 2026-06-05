package cn.afeibaili.jump.common.resource


/**
 * # 资源加载器接口
 *
 *@author AfeiBaili
 *@version 2026/6/2 23:00
 */

interface Loader<Type> {
    fun load(): Type

    companion object {
        val userPath: String = System.getProperty("user.dir")
    }
}