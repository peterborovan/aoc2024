import java.io.File

fun main() {
    val inputt = File("in/Day05.txt").readText().trimIndent()

    val parts = inputt.split("\n\n")
    val p1 = parts[0].lines()
    val p2 = parts[1].lines()

    val rules = mutableMapOf<Int, MutableSet<Int>>()
    p1.forEach{
        val (x, y) = it.split("|").map { it.toInt() }
        if (rules[x] == null) {
            rules[x] = mutableSetOf(y)
        } else {
            rules[x]!!.add(y)
        }
    }

    fun isOk(l: List<Int>) =
        rules.all{ (x, greaters) ->
            greaters.all{ y -> !(x in l && y in l && l.indexOf(x) > l.indexOf(y))
            }
        }

    val part1 = p2.map{ it.split(",").map { it.toInt() }}
        .filter { isOk(it) }
        .sumOf { it[it.size / 2] }
    println("Part1: $part1")

    fun toposort(l: List<Int>): List<Int> {
        val g = mutableMapOf<Int, MutableSet<Int>>()
        val d = mutableMapOf<Int, Int>()
        val s = l.toSet()
        rules.filter{ it.key in s}
            .forEach{ (x, greaters) ->
                greaters.filter{it in s}
                    .forEach{y ->
                        g.computeIfAbsent(x) { mutableSetOf() }.add(y)
                        d[y] = (d[y]?:0) + 1
                    }
                }
        l.forEach{ d.putIfAbsent(it, 0) }

        val sorted = mutableListOf<Int>()
        val queue = ArrayDeque(d.filter { it.value == 0 }.keys)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            sorted.add(current)
            for (next in g[current] ?: emptySet()) {
                d[next] = d[next]!! - 1
                if (d[next] == 0) {
                    queue.addLast(next)
                }
            }
        }
        return sorted
    }

    val part2 = p2.map{ it.split(",").map { it.toInt() }}
        .filter { !isOk(it) }
        .map { toposort(it) }
        .sumOf { it[it.size / 2] }
    println("Part2: $part2")
}
