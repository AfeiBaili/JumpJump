import kotlin.test.Test

/**
 * 测试 Regex
 *
 * @author AfeiBaili
 * @version 2026/6/3 13:14
 */

class TestRegex {
    @Test
    fun test3(){
        println("\\{.*}".toRegex().matches("}{d"))
    }

    @Test
    fun test2() {
        val listOf: List<String> = listOf(
            "{a}",
            "{}",
            " {xx} ",
        )

        listOf.forEach { key ->
            val value: String? = "\\{(\\w+)}".toRegex().find(key)?.groups?.get(1)?.value
            if (value == null) {
                println("$key value is $value")

            } else {
                println(value)
            }
        }
    }

    @Test
    fun test1() {
        val listOf: List<String> = listOf(
            "#",
            " #",
            "  #",
        )

        for (string in listOf) {

            println(string.split("\\s".toRegex()).filter { it.isNotBlank() }[0])

        }
    }
}