import java.io.File

fun main() {
    fun solve22(dxA: Long, dyA: Long, dxB: Long, dyB: Long, prizeX: Long, prizeY: Long): Long? {
        val d = dxA*dyB-dxB*dyA
        val a = prizeX*dyB-prizeY*dxB
        val b = dxA*prizeY-prizeX*dyA
        if (b % d != 0L) return null
        if (a % d != 0L) return null
        return (b/d)+3*(a/d)
    }
    data class Machine(val dxA: Long, val dyA: Long, val dxB: Long, val dyB: Long, val px: Long, val py: Long)
    val input = File("in/Day13.txt").readText().trimIndent().lines()

    for (part2 in listOf(false, true)) {
        val machines = emptyList<Machine>().toMutableList()
        var dxA = 0L
        var dyA = 0L
        var dxB = 0L
        var dyB = 0L
        for (l in input) {
            if (l.startsWith("Button A:")) {
                val parts = l.replace("Button A: X+", "").replace(" Y+", "").split(",").map { it.toLong() }
                dxA = parts[0]
                dyA = parts[1]
            } else if (l.startsWith("Button B:")) {
                val parts = l.replace("Button B: X+", "").replace(" Y+", "").split(",").map { it.toLong() }
                dxB = parts[0]
                dyB = parts[1]
            } else if (l.startsWith("Prize: ")) {
                val parts = l.replace("Prize: X=", "").replace(" Y=", "").split(",").map { it.toLong() }
                val px = parts[0]
                val py = parts[1]
                machines.add(
                    Machine(
                        dxA,
                        dyA,
                        dxB,
                        dyB,
                        px + if (part2) 10000000000000 else 0,
                        py + if (part2) 10000000000000 else 0
                    )
                )

            }
        }
        val part1 = machines.map { machine -> solve22(machine.dxA, machine.dyA, machine.dxB, machine.dyB, machine.px, machine.py)
        }.filter { it != null }.map { it!! }.sum()
        println("Part: $part1")
    }
}
