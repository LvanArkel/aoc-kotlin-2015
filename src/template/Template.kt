package template

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.test.assertEquals
import kotlin.time.Clock

typealias Input = List<String>

fun parse(raw: String): Input {
    return raw.lines().filter(String::isNotEmpty)
}

fun part1(input: Input, debug: Boolean = false): Int {
    return 0
}

fun part2(input: Input, debug: Boolean = false): Int {
    return 0
}

fun main() {
    val input = parse(readInput("Day01"))

    val start1 = Clock.System.now()
    val part1 = part1(input)
    val end1= Clock.System.now()
    println("Part 1: $part1 in (${end1 - start1} sec)")

    val start2 = Clock.System.now()
    val part2 = part2(input)
    val end2= Clock.System.now()
    println("Part 2: $part2 in (${end2 - start2} sec)")
}

class Tests {
    val testInput = """FOO"""
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {
            assertEquals(-1, part1(parsed, debug = true))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
            assertEquals(-1, part2(parsed, debug = true))
        }

    }

}
