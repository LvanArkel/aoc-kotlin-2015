package day08

import readInput
import kotlin.test.assertEquals

typealias Input = List<String>

fun parse(raw: String): Input {
    return raw.lines()
}

fun part1(input: Input): Int {
    val regex1 = Regex("""\\[^x]""")
    val regex2 = Regex("""\\x..""")
    return input.sumOf { line ->
        val stripped = line.substring(1, line.length - 1)
        val replaced = stripped
            .replace(regex1, "#")
            .replace(regex2, "#")
        line.length - replaced.length
    }
}

fun part2(input: Input): Int {
    val regex1 = Regex("""\\[^x]""")
    val regex2 = Regex("""\\x..""")
    return input.sumOf { line ->
        val replaced = line
            .replace(regex1, "####")
            .replace(regex2, "#####")
        replaced.length+4 - line.length
    }
}

fun main() {
    val testInput = """""
        |"abc"
        |"aaa\"aaa"
        |"\x27"
    """.trimMargin()
    val parsed = parse(testInput)

    assertEquals(12, part1(parsed))

    val input = parse(readInput("Day08"))
    println("Part 1: ${part1(input)}")

    assertEquals(19, part2(parsed))

    println("Part 2: ${part2(input)}")
}
