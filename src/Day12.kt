import java.io.File

fun rozky(a : List<Pair<Int, Int>>) =
    a.sumOf { (x,y) ->
        listOf(
            (x-1 to y+0) to (x+0 to y-1), (x+1 to y+0) to (x+0 to y-1), (x-1 to y+0) to (x+0 to y+1), (x+1 to y+0) to (x+0 to y+1))
            .count { (e,f) -> (e !in a) && (f !in a) } +
        listOf(
            Triple(x - 1 to y, x to y - 1, x - 1 to y - 1), Triple(x + 1 to y, x to y - 1, x + 1 to y - 1),
            Triple(x - 1 to y, x to y + 1, x - 1 to y + 1), Triple(x + 1 to y, x to y + 1, x + 1 to y + 1))
            .count { (e1,e2,f)  -> e1 in a && e2 in a && f !in a
        }
    }

fun obsahObvodRozky(a: List<String>): Pair<Int,Int> {
    val rows = a.size
    val cols = a[0].length
    val visited = mutableSetOf<Pair<Int, Int>>()
    val results = mutableListOf<Triple<Int, Int,Int>>()
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            if (Pair(r, c) !in visited) {
                val queue = mutableListOf(Pair(r, c))
                val plant = a[r][c]
                val pp = Pair(r, c)
                visited.add(pp)
                val area = listOf(pp).toMutableList()
                var obsah = 0
                var obvod = 0
                while (queue.isNotEmpty()) {
                    val (x, y) = queue.removeAt(0)
                    obsah++
                    for ((nx, ny) in listOf(x - 1 to y, x to y - 1, x + 1 to y, x to y + 1)) {
                        if (nx !in a.indices || ny !in a[nx].indices || a[nx][ny] != plant) {
                            obvod++
                            continue
                        }
                        if (Pair(nx, ny) !in visited) {
                            val p = Pair(nx, ny)
                            visited.add(p)
                            queue.add(p)
                            area.add(p)
                        }
                    }
                }
                results.add(Triple(obsah, obvod , rozky(area)))
            }
        }
    }
    return Pair(results.sumOf { (r, o, _) -> o * r }, results.sumOf { (r, _, c) -> c * r })
}

fun main() {
    val input = File("in/Day12.txt").readText().trimIndent().lines()
    val (part1, part2) = obsahObvodRozky(input)
    println("Part1: $part1")
    println("Part2: $part2")
}
