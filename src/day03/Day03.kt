package day03

import readInput
import kotlin.test.assertEquals

typealias Input = String

fun main() {
    fun part1(input: Input): Int {
        val visited = HashSet<Pair<Int, Int>>()
        visited.add(0 to 0)
        input.fold(0 to 0, { (x, y), c ->
            val newCoord = when (c) {
                '^' -> x to y+1
                '>' -> x+1 to y
                'v' -> x to y-1
                '<' -> x-1 to y
                else -> 0 to 0
            }
            visited.add(newCoord)
            newCoord
        })

        return visited.size
    }

    fun part2(input: Input): Int {
        val visited = HashSet<Pair<Int, Int>>()
        visited.add(0 to 0)
        input.fold((0 to 0) to (0 to 0), { (coord0, coord1), c ->
            val (x, y) = coord0
            val newCoord = when (c) {
                '^' -> x to y+1
                '>' -> x+1 to y
                'v' -> x to y-1
                '<' -> x-1 to y
                else -> 0 to 0
            }
            visited.add(newCoord)
            coord1 to newCoord
        })

        return visited.size
    }

    // Test if implementation meets criteria from the description, like:
    assertEquals(2, part1(">"))
    assertEquals(4, part1("^>v<"))
    assertEquals(2, part1("^v^v^v^v^v"))

    assertEquals(3, part2("^v"))
    assertEquals(3, part2("^>v<"))
    assertEquals(11, part2("^v^v^v^v^v"))


    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInputLines("Day03_test")
//    assertEquals(1, part1(testInput))

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
