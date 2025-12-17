package day10

import readInput
import kotlin.test.assertEquals

fun looksay(input: List<Int>, iterations: Int): Int {
    if (iterations == 0) {
        return input.size
    }

    val newList = mutableListOf<Int>()
    var d = input.first()
    var c = 1
    for (digit in input.drop(1)) {
        if (digit == d) {
            c += 1
        } else {
            newList.add(c)
            newList.add(d)
            d = digit
            c = 1
        }
    }
    newList.add(c)
    newList.add(d)
    return looksay(newList, iterations - 1)
}

fun main() {
    val testInput = """1""".map { c -> c.digitToInt() }

    assertEquals(2, looksay(testInput, 1))
    assertEquals(2, looksay(testInput, 2))
    assertEquals(4, looksay(testInput, 3))
    assertEquals(6, looksay(testInput, 4))
    assertEquals(6, looksay(testInput, 5))

    val input = "1113122113".map { c -> c.digitToInt() }
    println("Part 1: ${looksay(input, 40)}")

    println("Part 2: ${looksay(input, 50)}")
}
