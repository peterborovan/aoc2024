import java.io.File

fun main() {
    val width = 101
    val height = 103
    val r = emptyList<Pair<Pair<Int, Int>, Pair<Int, Int>>>().toMutableList()
    val input = File("in/Day14.txt").readText().trimIndent().lines()
    for (l in input) {
        val parts = l.replace("p=", "").replace(" v=", ",").split(",").map { it.toInt() }
        r.add((parts[0] to parts[1]) to (parts[2] to parts[3]))
    }
    for (seconds in 0..10000) {
        val quads = Array(4){0}
        val a = Array(200) { CharArray(200) { '.' } }
        fun maxComponent():Int {
            val visited = emptyList<Pair<Int,Int>>().toMutableList()
            var maxSize = -1
            for (r in a.indices)
                for (c in a[r].indices) {
                    if (a[r][c] != '.') {
                        val queue = listOf(r to c).toMutableList()
                        visited.add(r to c)
                        var size = 1
                        while (!queue.isEmpty()) {
                            val (x,y) = queue.removeAt(0)
                            size++
                            for ((nx,ny) in listOf((x-1 to y+0),(x+1 to y+0),(x+0 to y-1),(x+0 to y+1))) {
                                if (nx in a.indices && ny in a[nx].indices && a[nx][ny] != '.' && nx to ny !in visited)
                                    queue.add(nx to ny)
                                    visited.add(nx to ny)
                            }
                        }
                        if (size > maxSize)
                            maxSize = size
                    }
                }
            return maxSize
        }
            for ((p, v) in r) {
                var (x, y) = p
                repeat(seconds) {
                    val (vx, vy) = v
                    val nx = (x + vx) % width
                    val ny = (y + vy) % height
                    x = if (nx < 0) nx + width else nx
                    y = if (ny < 0) ny + height else ny
                }
                val w2 = width/2
                val h2 = height/2
                if (x != w2 && y != h2)
                    if (x < w2) if (y < h2) quads[0]++ else quads[2]++ else if (y < h2) quads[1]++ else quads[3]++
                a[x][y] = '#'
            }
            if (seconds == 100)
                println("Part1: $seconds ${quads.toList().reduce { x, y -> x * y }}")
            val maxComp = maxComponent()
            if (maxComp > 20) {
                a.forEach { println(it.concatToString()) }
                println("$seconds ${maxComponent()}")
            }
    }
}
