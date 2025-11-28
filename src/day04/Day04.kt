package day04

import md5
import kotlin.test.assertEquals

typealias Input = String

fun main() {
    fun part1(input: Input): Int {
        val pattern = "00000"
        generateSequence(0, Int::inc).forEach { i ->
            if ("$input$i".md5().startsWith(pattern)) {
                return i
            }
        }
        return input.length
    }

    fun part2(input: Input): Int {
        val pattern = "000000"
        generateSequence(0, Int::inc).forEach { i ->
            if ("$input$i".md5().startsWith(pattern)) {
                return i
            }
        }
        return input.length
    }

    // Test if implementation meets criteria from the description, like:
    assertEquals(609043, part1("abcdef"))
    assertEquals(1048970, part1("pqrstuv"))

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInputLines("Day01_test")
//    assertEquals(1, part1(testInput))

    // Read the input from the `src/Day01.txt` file.
    println("Part 1: ${part1("iwrupvqb")}")
    println("Part 2: ${part2("iwrupvqb")}")
}
