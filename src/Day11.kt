import java.io.File

fun main() {
    val input = File("in/Day11.txt").readText().lines()[0].split(" ").map { it.toLong() }
    println("Part1: ${simulate(25, input)}")
    println("Part2: ${simulate(75, input)}")
}

fun simulate(cycles: Int, stones: List<Long>): Long {
    var m = stones.map { stone -> Pair(stone, 1L) }.toMap()
    repeat(cycles) {
        val newm = mutableMapOf<Long, Long>()
        m.forEach{ (s, c) ->
            fun update(i :Long) {
                newm[i] = newm.getOrDefault(i, 0) + c
            }
            val str = s.toString()
            when {
                s == 0L -> update(1)
                str.length % 2 == 0 -> {
                    val len2 = str.length / 2
                    update(str.substring(0, len2).toLong())
                    update(str.substring(len2).toLong())
                }
                else -> update(2024*s)
            }
        }
        m = newm
    }
    return m.values.sum()
}
