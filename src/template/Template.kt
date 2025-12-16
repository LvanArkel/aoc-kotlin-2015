package template

import readInput
import kotlin.test.assertEquals

typealias Input = List<String>

fun parse(raw: String): Input {
    return raw.lines().filter(String::isNotEmpty)
}

fun part1(input: Input): Int {
    return input.size
}

fun part2(input: Input): Int {
    return input.size
}

fun main() {
    val testInput = """FOO"""
    val parsed = parse(testInput)

    assertEquals(-1, part1(parsed))

    val input = parse(readInput("Day01"))
    println("Part 1: ${part1(input)}")

    assertEquals(-1, part2(parsed))

    println("Part 2: ${part2(input)}")
}
