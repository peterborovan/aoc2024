import java.io.File

fun main() {
    val input = File("in/Day10.txt").readText().trimIndent()
        .lines().map { line -> line.map { it.toString().toInt() }.toIntArray() }.toTypedArray()
    val rows = input.size
    val cols = input[0].size

    fun v(r: Int, c: Int) =
        r in 0 until rows && c in 0 until cols

    val th = (0 until rows).flatMap { r-> (0 until cols).flatMap { c -> listOf(Pair(r,c)) }}
        .filter { (r,c) -> input[r][c] == 0 }

    val part1 = th.sumOf { trailhead ->
        val dest = mutableSetOf<Pair<Int, Int>>()
        val visited = Array(rows) { BooleanArray(cols) }
        val queue = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
        queue.add(Pair(Pair(trailhead.first, trailhead.second), 0))
        while (queue.isNotEmpty()) {
            val (x, h) = queue.removeFirst()
            val (r, c) = x
            if (v(r, c) && !visited[r][c] && input[r][c] == h) {
                visited[r][c] = true
                nexti(r, c).forEach { (nr, nc) -> queue.add(Pair(Pair(nr, nc), h + 1)) }
                if (h == 9) dest.add(Pair(r,c))
            }
        }
        dest.size
    }
    println("Part1: $part1")

    val part2 = th.sumOf { trailhead ->
        val dp = Array(rows) { Array(cols) { IntArray(10) } }
        dp[trailhead.first][trailhead.second][0] = 1
        (0 until 9).forEach { h ->
            (0 until rows).forEach { r ->
                (0 until cols).forEach { c ->
                    if (dp[r][c][h] > 0) {
                        nexti(r, c).forEach { (nr, nc) ->
                            if (v(nr, nc) && input[nr][nc] == h + 1)
                                dp[nr][nc][h + 1] += dp[r][c][h]
                        }
                    }
                }
            }
        }
        (0 until rows).sumOf { row -> (0 until cols).sumOf { col -> dp[row][col][9] } }
    }
    println("Part1: $part2")
}
private fun nexti(r: Int, c: Int) = listOf(r - 1 to c, r + 1 to c, r to c - 1, r to c + 1)

