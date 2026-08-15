package cn.afeibaili.jump.common.util

import cn.afeibaili.gl.logger.Logger
import cn.afeibaili.gl.logger.LoggerFactory

inline fun logger(action: () -> String): Logger {
    val name: String = action()
    return LoggerFactory.create(name)
}