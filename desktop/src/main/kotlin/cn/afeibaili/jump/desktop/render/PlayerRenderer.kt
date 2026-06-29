package cn.afeibaili.jump.desktop.render

import cn.afeibaili.jump.desktop.Application
import cn.afeibaili.jump.desktop.entity.Player
import cn.afeibaili.jump.desktop.logic.LogicThread


/**
 * # 玩家渲染器
 *
 * @author AfeiBaili
 * @version 2026/6/28 01:55
 */

class PlayerRenderer : Renderable {
    val player get() = Player.self
    val logic get() = LogicThread.self
    val camera get() = Application.camera

    override fun render() {
        // 插值渲染
        val alpha = logic.accumulationSecondes / logic.step // 当前步进度
        val renderX = player.pvx + (player.x - player.pvx) * alpha
        val renderY = player.pvy + (player.y - player.pvy) * alpha
        camera.translation(renderX, renderY)
    }
}