package template

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
    val input = parse(readInput("Day01"))
    println("Part 1: ${part1(input)}")

    println("Part 2: ${part2(input)}")
}

class Tests {
    val testInput = """FOO"""
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {
            assertEquals(-1, part1(parsed))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
            assertEquals(-1, part2(parsed))
        }

    }

}
