import java.io.File

fun main() {
    val input = File("in/Day15.txt").readText().trimIndent().split("\n\n")
    val playground = input[0].lines()
    val moves = input[1].lines().joinToString(separator = "")
    val rows = playground.size
    val cols = playground[0].length
    val dirs = mapOf('<' to Pair(0, -1), '>' to Pair(0, 1), '^' to Pair(-1, 0), 'v' to Pair(1, 0))

    for (part2 in listOf(false, true)) {
        val os = mutableSetOf<Pair<Int, Int>>()
        val lefts = mutableSetOf<Pair<Int, Int>>()
        val rights = mutableSetOf<Pair<Int, Int>>()
        var robot = Pair(0, 0)
        val wall = mutableSetOf<Pair<Int, Int>>()
        fun printPlayground() {
            for (r in 0 until rows) {
                for (c in 0 until if (part2) cols * 2 else cols) {
                    if (r to c == robot) print("@")
                    else if (r to c in lefts) print("[")
                    else if (r to c in rights) print("]")
                    else if (r to c in wall) print("#")
                    else print('.')
                }
                println()
            }
        }
        for (r in playground.indices) {
            for (c in playground[r].indices) {
                when (playground[r][c]) {
                    '#' -> if (part2) {wall.add(Pair(r, 2 * c));wall.add(Pair(r, 2 * c + 1))} else wall.add(Pair(r, c))
                    'O' -> if (part2) {lefts.add(Pair(r, 2 * c));rights.add(Pair(r, 2 * c + 1))} else os.add(Pair(r, c))
                    '@' -> if (part2) robot = Pair(r, 2 * c) else  robot = Pair(r, c)
                }
            }
        }
        for (move in moves) {
            //printPlayground()
            //println("move $move")
            val (dr, dc) = dirs[move]!!
            if (!part2) {
                val target = Pair(robot.first + dr, robot.second + dc)
                if (target in wall) continue
                if (target in os) {
                    var b = 0
                    while (true) {
                        b++
                        if (Pair(target.first + b * dr, target.second + b * dc) in os) continue
                        if (Pair(target.first + b * dr, target.second + b * dc) in wall) {
                            b = -1
                            break
                        } else
                            break
                    }
                    if (b > 0) {
                        val boxTarget = Pair(target.first + b * dr, target.second + b * dc)
                        os.remove(target)
                        os.add(boxTarget)
                    } else
                        continue
                }
                robot = target
            } else {  // part2
                val y = Pair(robot.first + dr, robot.second + dc)
                val targets = emptyList<Pair<Int, Int>>().toMutableList()
                if (y in lefts || y in rights) {
                    targets.add(y)
                    if (y in lefts)
                        targets.add(y.first to y.second + 1)
                    else
                        targets.add(y.first to y.second - 1)
                }
                var i = 0
                while (i < targets.size) {
                    val t = targets[i]
                    i++
                    //println("t=$t $targets")
                    val nt = t.first + dr to t.second + dc
                    //println("nt = $nt ${nt in walls}")
                    if (nt in targets) continue
                    if (t in wall) {
                        break
                    }
                    if (nt in lefts) {
                        // println("boxesl=$boxesl $t")
                        val x = nt.first to nt.second + 1
                        targets.add(nt)
                        if (x !in targets) targets.add(x)
                    }
                    if (nt in rights) {
                        // println("boxesr=$boxesr $t")
                        val x = nt.first to nt.second - 1
                        targets.add(nt)
                        if (x !in targets) targets.add(x)
                    }
                    //println("targets = $targets")
                }
                val possible =
                    if (targets.isEmpty())
                        robot.first + dr to robot.second + dc !in wall
                    else
                        targets.all { t -> (t.first + dr to t.second + dc) !in wall }
                //println("possible = $possible")
                // println("allT = ${targets.toSet()}")
                if (possible) {
                    val newLefts = emptyList<Pair<Int, Int>>().toMutableSet()
                    val newRights = emptyList<Pair<Int, Int>>().toMutableSet()
                    for (t in targets.toSet()) {
                        if (t in lefts) {
                            lefts.remove(t)
                            newLefts.add(t.first + dr to t.second + dc)
                        }
                        if (t in rights) {
                            rights.remove(t)
                            newRights.add(t.first + dr to t.second + dc)
                        }
                    }
                    robot = Pair(robot.first + dr, robot.second + dc)
                    lefts.addAll(newLefts)
                    rights.addAll(newRights)
                }
            }
        }
        if (!part2)
            println("part1: ${os.sumOf { (r, c) -> 100 * r + c }}")
        else
            println("part2: ${lefts.sumOf { (r, c) -> 100 * r + c }}")
    }
}
