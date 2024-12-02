import java.io.File

fun safe(m: MutableList<Int>) =
    (0 until m.size - 1).all { i -> (m[i + 1] - m[i]) in 1..3}
            ||
    (0 until m.size - 1).all { i -> (m[i + 1] - m[i]) in -3..-1}

fun safeWD(m: MutableList<Int>) =
    safe(m) || (m.indices).any{ safe(m.toMutableList().apply { removeAt(it) }) }

fun main() {
    val input = File("in/Day02.txt").readLines().map { it.split(" ").map(String::toInt) }.map{it.toMutableList()}
    println("Part1: ${input.count { safe(it) }}")
    println("Part2: ${input.count { safeWD (it) }}")
}