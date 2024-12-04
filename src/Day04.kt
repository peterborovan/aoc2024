import java.io.File

fun main() {
    val a = File("in/Day04.txt").readLines()
    val rows = a.size
    val cols = a[0].length
    val directions = (-1..1).flatMap { dx ->  (-1..1).flatMap { dy -> listOf(Pair(dx,dy))}}

    val part1 = (0 until rows).sumOf { x ->
        (0 until cols).sumOf { y ->
            (directions).count { (dx, dy) ->
                ("XMAS".indices).all { i ->
                    val nx = x + i * dx
                    val ny = y + i * dy
                    (nx in 0 until rows && ny in 0 until cols && a[nx][ny] == "XMAS"[i])
                }
            }
        }
    }
    println("Part1: ${part1}")

    val part2 = (1 until rows - 1).sumOf { x ->
        (1 until cols - 1).count { y ->
            listOf(""+a[x-1][y-1]+a[x][y]+a[x+1][y+1], ""+a[x-1][y+1]+a[x][y]+a[x+1][y-1])
                .all { it in listOf("MAS", "SAM")}
        }
    }
    println("Part2: ${part2}")
}
