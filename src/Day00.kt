import java.io.File
import java.lang.NumberFormatException

fun main() {
    val input1 = File("in/day00.txt").readText()

    val input2 = File("in/day00.txt").readLines()
    //val parts = input1.split("-").map { it.toInt() }
    //val ints = input2.map { it.toInt() }

    val regexp = """(\d)-(\d)-(\d)""".toRegex()
    val input3 = "1-2-3"
    val (a,b,c) = regexp.find(input3)?.destructured ?: error("bad input")

    val matches = regexp.matches(input3)
    for (mr in regexp.findAll(input3)) {
        println(mr.groups)
        for (g in mr.groups) {
            try {
                if (g != null) {
                    println("${g.value.toInt()}, ${g.range}")
                }
            } catch (e : NumberFormatException) {
                println("${g.toString()}") // group 0
            }
        }

    }
    input3.substringBefore("-")
    input3.substringAfter("-")
    input3.substringBeforeLast("-")
    // test

    val x = input2.map {
        val (a,b,c) = regexp.find(it)?.destructured ?: error("bad input")
        a.toInt()+b.toInt()+c.toInt()
    }.sum()
    println(x)
}