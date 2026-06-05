package cn.afeibaili.jump.common.resource


/**
 * # 获取器
 *
 * @author AfeiBaili
 * @version 2026/6/5 22:17
 */

interface Getter<Param, Result> {
    fun get(param: Param): Result
}