import java.io.File

fun main() {
    val input = File("in/Day07.txt").readText().trimIndent().lines().map{ it.split(": ")}
        .map{ (lhs, numbs) -> Pair(lhs.toLong(), numbs.split(" ").map {it.toLong()})}
    for (p in listOf("+*", "+*|").map{ it.toCharArray()})
        println("Part: ${input.sumOf { (lhs, numbs) ->
            if (eval(  numbs[0], numbs.drop(1), p,  lhs)) lhs else 0L }}")
}
//haskellish
fun eval(acc:Long, numbs: List<Long>, ops: CharArray, lhs: Long):Boolean =
    if (numbs.isEmpty()) acc == lhs
    else
        ops.any {
            eval(when (it) {
                '+' -> acc + numbs[0]
                '*' -> acc * numbs[0]
                '|' -> (acc.toString() + numbs[0].toString()).toLong()
                else-> acc
            }, numbs.drop(1), ops, lhs)
        }
