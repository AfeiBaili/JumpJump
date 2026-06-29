import kotlin.random.Random
import kotlin.test.Test

/**
 * 测试颜色
 *
 * @author AfeiBaili
 * @version 2026/6/29 22:30
 */

class TestRGB {
    @Test
    fun test1() {
        val nextInt: Int = Random(System.currentTimeMillis()).nextInt()
        println(nextInt.toBinaryString())

        val rgb: Int = (nextInt ushr 8)
        println(rgb.toBinaryString())
        println((0xff shl 24 or rgb).toBinaryString())
    }

    fun Int.toBinaryString(): String {
        return Integer.toBinaryString(this).padStart(32, '0')
    }
}