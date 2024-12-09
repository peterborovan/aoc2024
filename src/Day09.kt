import java.io.File

data class Chunk(val id: Int, val len:Int,val  pos:Int)
fun main() {

    val input = File("in/Day09.txt").readText().trimIndent()
    val b = mutableListOf<Int?>()
    var isID = true
    var id = 0
    for (char in input.toCharArray()) {
        val length = char.digitToInt()
        repeat(length) {
            b.add(if (isID) id else null)
        }
        if (isID) id++
        isID = !isID
    }
    //println("$(blocks.joinToString(\"\") { it?.toString() ?: \".\" }}")
    var w = 0
    for (r in b.size - 1 downTo 0) {
        if (b[r] != null) {
            while (b[w] != null) w++
            //println("$w")
            if (w >= r) break
            b[w] = b[r]
            b[r] = null
            //println("$w <- $r")
            w++
        }
    }
    //println("$(blocks.joinToString(\"\") { it?.toString() ?: \".\" }}")
    val part1 = b.withIndex()
        .filter { it.value != null }
        .sumOf { it.index.toLong() * it.value!! }
    println("Part1: $part1")

    val disk = emptyList<Chunk>().toMutableList()
    var pos = 0
    var maxID = 0
    for (i in input.indices) {
        val len = input[i].digitToInt()
        if (i % 2 == 0) {
            disk.add(Chunk(i/2, len, pos))
            if (i/2 > maxID)
                maxID = i/2
        } else {
            disk.add(Chunk(-1, len, pos))
        }
        pos += len
    }
    for(id in maxID downTo 0) {
//        for(x in disk) {
//            print(if (x.id < 0) ".".repeat(x.len) else x.id.toString().repeat(x.len))
//        }
//        println()
        var idIndex = -1
        for(j in disk.indices) {
            if (disk[j].id == id)
                idIndex = j
        }
        val najvacsi = disk[idIndex]
        for(j in disk.indices) {
            if (disk[j].id == -1 && disk[j].len >= disk[idIndex].len
                && disk[j].pos < najvacsi.pos   ) {
                val medzera = disk[j]
                disk.removeAt(idIndex)
                disk.add(idIndex, Chunk(-1, najvacsi.len, najvacsi.pos))
                disk.removeAt(j)
                disk.add(j, Chunk(id, najvacsi.len, medzera.pos))
                if (medzera.len-najvacsi.len > 0)
                    disk.add(j+1, Chunk(-1, medzera.len-najvacsi.len, medzera.pos+najvacsi.len))
                break
            }
        }
    }
    var part2 = 0L
    for(x in disk) {
        if (x.id >= 0)
            part2 += (x.pos.toLong() + x.pos +x.len-1)*(x.len)/2 * x.id
    }
    println("Part2: $part2")
}

