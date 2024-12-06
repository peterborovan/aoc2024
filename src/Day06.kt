import java.io.File

fun main() {
    val a = File("in/Day06.txt").readText().trimIndent().lines().map { it.toCharArray() }.toMutableList()
    val dirs = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    var sx = 0
    var sy = 0
    var sd = 0
    a.indices.forEach { r ->
        a[r].indices.forEach { c ->
            if (a[r][c] !in ".#") {
                sx = r; sy = c
                sd = "^>V<".indexOf(a[r][c])
            }
        }
    }
    fun obstacle(part2: Boolean, ox :Int, oy:Int): Int {
        val visited1 = mutableSetOf<Pair<Int,Int>>()
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
        var x = sx
        var y = sy
        var d = sd
        if (part2 && (a[ox][oy] != '.' || a[ox][oy] == 'S')) return 0

        while (true) {
            visited1.add(Pair(x,y))
            if (part2 && !visited.add(Pair(Pair(x, y), d))) return 1
            val (dx,dy) = dirs[d]
            val nx = x + dx
            val ny = y + dy
            if (!(nx in a.indices && ny in a[0].indices))
                return if (part2) 0 else visited1.size
            if (a[nx][ny] == '#' || (part2 && nx == ox && ny == oy))
                d = (d + 1) % 4
            else {
                x = nx
                y = ny
            }
        }
    }
    val part1 = obstacle(false, 0, 0)
    println("Part1: $part1")

    val part2 = a.indices.sumOf { r ->
        a[r].indices.sumOf { c ->
            obstacle(true, r, c)
        }
    }
    println("Part2: $part2")
}
