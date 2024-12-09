import java.io.File

fun main() {
    val input = File("in/Day08.txt").readText().trimIndent().lines()
    val rows = input.size
    val cols = input[0].length

    val antenas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (r in 0 until rows) {
        for (c in 0 until cols)  {
            if (input[r][c] != '.') {
                antenas.computeIfAbsent(input[r][c]) { mutableListOf() }.add(Pair(r, c))
            }
        }
    }
    fun anodes1(p1:Pair<Int,Int>, p2:Pair<Int,Int>):Set<Pair<Int,Int>> {
        if (p1 == p2) return emptySet()
        val (r1,c1) = p1
        val (r2,c2) = p2
        val dr = r1 - r2
        val dc = c1 - c2
        return setOf(Pair(r2-dr, c2-dc), Pair(r1+dr, c1+dc)).filter{(r,c) -> r in 0..<rows && c in 0..<cols }.toSet()
    }
    fun anodes2(p1:Pair<Int,Int>, p2:Pair<Int,Int>):Set<Pair<Int,Int>> {
        if (p1 == p2) return emptySet()
        val (r1, c1) = p1
        val (r2, c2) = p2
        return (0 until rows).flatMap { r -> (0 until cols).flatMap { c -> listOf(Pair(r, c)) }
        }.filter { (r, c) -> (r - r1) * (c - c2) == (c - c1) * (r - r2)
        }.toSet()
    }
    val part1 =
        antenas.values.flatMap { pos -> pos.flatMap { p1 -> pos.flatMap { p2 -> anodes1(p1, p2) } } }.toSet().size
    println("Part1: $part1")
    val part2 =
        antenas.values.flatMap { pos -> pos.flatMap { p1 -> pos.flatMap { p2 -> anodes2(p1, p2) } } }.toSet().size
    println("Part2: $part2")
}
