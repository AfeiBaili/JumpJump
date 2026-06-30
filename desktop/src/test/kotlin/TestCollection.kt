import kotlin.test.Test

/**
 * 测试集合
 *
 * @author AfeiBaili
 * @version 2026/6/30 20:39
 */

class TestCollection {
    @Test
    fun test1() {
        val lists: List<List<Int>> = listOf(listOf(2, 3, 1), listOf(4, 5, 6), listOf(7, 8, 9))
        val map: List<Int> = lists.flatMap { it }
        println(map.joinToString("、"))

    }
}