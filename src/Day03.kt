import java.io.File

//xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))

fun main() {
    val input = File("in/Day03.txt").readLines()
    var s = 0L
    for(l in input) {
        val regex = Regex("""mul\((\d+),(\d+)\)""")
        s += regex.findAll(l)
            .map { matchResult ->
                val (x, y) = matchResult.destructured
                x.toInt() * y.toInt()
            }
            .sum()
        }
    println("Part1: ${s}")
    s = 0L
    var on = true
    for(l in input) {
        for(i in 0 until l.length) {
                val r = Regex("""^mul\((\d+),(\d+)\).*""")
                if (l.startsWith("do()", i)) {
                    on = true
                } else if (l.startsWith("don't()", i)) {
                    on = false
                } else  if (r.find(l.substring(i),0) != null) {
                    val mr = r.find(l.substring(i),0)
                    val (x,y) = mr!!.destructured
                    if (on)
                        s += x.toLong() * y.toLong()
                }
        }
    }
    println("Part2: ${s}")
}
