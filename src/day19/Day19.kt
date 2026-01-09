package day19

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import java.util.PriorityQueue
import kotlin.collections.emptySet
import kotlin.test.assertEquals
import kotlin.time.Clock

data class Input(
    val operations: List<Pair<String, String>>,
    val pattern: String,
)

fun parse(raw: String): Input {
    val lines = raw.lines()
    val operations = lines.dropLast(2).map { line ->
        val (a, b) = line.split(" => ", limit = 2)
        a to b
    }
    val pattern = lines.last()
    return Input(operations, pattern)
}

fun expandPattern(pattern: String, operation: Pair<String, String>): List<String> {
    val (start, end) = operation
    val regex = Regex(start)
    return regex.findAll(pattern).toList().map { matchResult ->
        pattern.replaceRange(matchResult.range, end)
    }
}

fun part1(input: Input): Int {
    return input.operations.fold(emptySet<String>(), { acc, operation ->
        val expansions = expandPattern(input.pattern, operation)
        acc + expansions.toSet()
    }).size
}

fun reducePattern(pattern: String, operation: Pair<String, String>): List<String> {
    val (start, end) = operation
    val regex = Regex(end)
    return regex.findAll(pattern).toList().map { matchResult ->
        pattern.replaceRange(matchResult.range, start)
    }
}

fun part2(input: Input): Int {
    val operations = input.operations
    val goal = input.pattern

    val queue = PriorityQueue<Pair<String, Int>> { a, b -> a.first.length.compareTo(b.first.length) }
    queue.add(goal to 0)

    while (queue.isNotEmpty()) {
        val (pattern, steps) = queue.remove()
        if (pattern == "e") {
            return steps
        }
        val substitutions = operations.fold(emptySet<String>()) { acc, operation ->
            val substitutions = reducePattern(pattern, operation)
            acc + substitutions.toSet()
        }
        queue.addAll(substitutions.map { it to steps + 1 })
    }
    return -1
}

fun main() {
    val input = parse(readInput("Day19"))

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
    val testInput = """H => HO
H => OH
O => HH

HOH"""
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {
            assertEquals(4, part1(parsed))
        }
    }

    @Nested
    inner class Part2 {
        val testInput = """e => H
e => O
H => HO
H => OH
O => HH

HOH"""
        val parsed = parse(testInput)

        @Test
        fun testPart2() {
            assertEquals(3, part2(parsed))
        }

        @Test
        fun testPart2Longer() {
            assertEquals(6, part2(parsed.copy(pattern = "HOHOHO")))
        }

    }

}
