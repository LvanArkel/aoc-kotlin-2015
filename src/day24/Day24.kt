package day24

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.collections.emptyList
import kotlin.test.assertEquals
import kotlin.time.Clock

typealias Input = List<Int>

fun parse(raw: String): Input {
    return raw.lines().filter(String::isNotEmpty).map{ it.toInt() }
}

fun combinations(list: Input, length: Int, total: Int): Sequence<List<Int>> = sequence {
    if (length == 0) {
        if (total == 0) {
            yield(emptyList())
        }
        return@sequence
    }
    if (list.isEmpty()) return@sequence
    val head = list.first()
    val tail = list.drop(1)
    for (combination in combinations(tail, length-1, total - head)) {
        yield(combination + head)
    }
    yieldAll(combinations(tail, length, total))
}

fun part1(input: Input, debug: Boolean = false): Long {
    val total = input.sum()
    val groupTotal = total / 3

    if (debug) println("Total ${total}, group: ${groupTotal}")

    for (length in 1 until input.size) {
        val validCombinations = combinations(input, length, groupTotal)
            .map { combination ->
                if (debug) { println("Found $combination ($length)") }
                combination
            }
            .toList()
        if (validCombinations.isNotEmpty()) {
            return validCombinations.minOf { combination ->
                combination
                    .map { it.toLong() }
                    .reduce(Long::times)
            }
        }
    }
    return -1
}

fun part2(input: Input, debug: Boolean = false): Long {
    val total = input.sum()
    val groupTotal = total / 4

    if (debug) println("Total ${total}, group: ${groupTotal}")

    for (length in 1 until input.size) {
        val validCombinations = combinations(input, length, groupTotal)
            .map { combination ->
                if (debug) { println("Found $combination ($length)") }
                combination
            }
            .toList()
        if (validCombinations.isNotEmpty()) {
            return validCombinations.minOf { combination ->
                combination
                    .map { it.toLong() }
                    .reduce(Long::times)
            }
        }
    }
    return -1
}

fun main() {
    val input = parse(readInput("Day24"))

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
    val testInput = """1
        |2
        |3
        |4
        |5
        |7
        |8
        |9
        |10
        |11
    """.trimMargin()
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {
            assertEquals(99L, part1(parsed, debug = true))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
            assertEquals(44, part2(parsed, debug = true))
        }

    }

}
