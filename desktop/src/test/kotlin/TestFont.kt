import kotlin.test.Test

/**
 * 字体测试
 *
 * @author AfeiBaili
 * @version 2026/7/4 14:03
 */

class TestFont {
    @Test
    fun test1() {
        val charList: List<Char> = (1..500000).map { it.toChar() }
        println(charList.joinToString("、"))
    }
}