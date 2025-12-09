package day05

import readInput
import kotlin.test.assertEquals

typealias Input = List<String>

fun parse(raw: String): Input {
    return raw.lines()
}

fun hasVowels(input: String): Boolean = input.count { c -> c in "aeiou" } >= 3
fun hasDouble(input: String): Boolean = input.windowed(size=2, step=1) { chars ->
    chars[0] == chars[1]
}.any{ it }
fun illegalStrings(input: String): Boolean = !input.contains(Regex("ab|cd|pq|xy"))

fun part1(input: Input): Int {
    return input.filter { hasVowels(it) && hasDouble(it) && illegalStrings(it) }.size
}

fun doublePair(input: String): Boolean = input.contains(Regex("(..).*\\1"))
fun enclosed(input: String): Boolean = input.contains(Regex("(.).\\1"))

fun part2(input: Input): Int {
    return input.filter { doublePair(it) && enclosed(it) }.size
}

fun main() {
    val testParsed = listOf(
        "ugknbfddgicrmopn",
        "aaa",
        "jchzalrnumimnmhp",
        "haegwjzuvuyypxyu",
        "dvszwmarrgswjxmb",
    )

    assertEquals(2, part1(testParsed))

    val input = parse(readInput("Day05"))
    println("Part 1: ${part1(input)}")

    val part2Test = listOf(
        "qjhvhtzxzqqjkmpb",
        "xxyxx",
        "uurcxstgmygtbstg",
        "ieodomkazucvgmuy",
    )

    assertEquals(2, part2(part2Test))

    println("Part 2: ${part2(input)}")
}