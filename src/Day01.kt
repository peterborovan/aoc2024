import java.io.File
import java.lang.NumberFormatException

fun main() {
    val input = File("in/day01.txt").readLines().map { it.split("   ")}
    //val parts = input.split("   ").map { it.toInt() }
    //val ints = input.map { it.toInt() }
    val a = input.map{it[0].toInt()}.toMutableList()
    val b = input.map{it[1].toInt()}.toMutableList()
    a.sort()
    b.sort()
    println("Part1: ${(0 until a.size).sumOf { Math.abs(b[it] - a[it]) }}")
    println("Part2: ${(0 until a.size).sumOf { i -> a[i]*b.count{it == a[i]} }}")
}