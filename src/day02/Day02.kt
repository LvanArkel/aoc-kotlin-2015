package day02

import readInputLines
import kotlin.test.assertEquals

typealias Input = List<String>

fun main() {
    fun part1(input: Input): Int {
        return input.sumOf { line ->
            val (l, w, h) = line.split("x").map { it.toInt() }
            val a = l*w
            val b = w*h
            val c = h*l
            2*(a + b + c) + minOf(a,b,c)
        }
    }

    fun part2(input: Input): Int {
        return input.sumOf { line ->
            val (l, w, h) = line.split("x").map { it.toInt() }
            val present = 2 * (l+w+h - maxOf(l,w, h))
            val bow = l*w*h
            present + bow
        }
    }

    // Test if implementation meets criteria from the description, like:
    assertEquals(58, part1(listOf("2x3x4")))
    assertEquals(43, part1(listOf("1x1x10")))

    assertEquals(34, part2(listOf("2x3x4")))
    assertEquals(14, part2(listOf("1x1x10")))

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInputLines("Day02_test")
//    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputLines("Day02")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
