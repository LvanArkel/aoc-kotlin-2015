package day17

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.test.assertEquals

typealias Input = List<Int>

fun parse(raw: String): Input {
    return raw.lines().filter(String::isNotEmpty).map(String::toInt)
}

fun part1Helper(bottles: List<Int>, amountLeft: Int): Int {
    if (bottles.isEmpty()) {
        if (amountLeft == 0) {
            return 1
        } else {
            return 0
        }
    }
    var total = 0
    val bottle = bottles.first()
    val tail = bottles.drop(1)
    // Take
    if (amountLeft >= bottle) {
        total += part1Helper(tail, amountLeft - bottle)
    }
    // Leave
    total += part1Helper(tail, amountLeft)
    return total
}

fun part1(input: Input, totalAmount: Int): Int {
    return part1Helper(input, totalAmount)
}

fun generateSequences(bottles: List<Int>): Sequence<List<Int>> = sequence {
    if (bottles.isEmpty()) {
        yield(emptyList())
        return@sequence
    }
    val head = bottles.first()
    val tail = bottles.drop(1)
    for (subsequence in generateSequences(tail)) {
        yield(subsequence)
        yield(listOf(head) + subsequence)
    }
}

fun part2(input: Input, totalAmount: Int): Int {
    val counts: HashMap<Int, Int> = hashMapOf()
    for (sequence in generateSequences(input)) {
        if (sequence.sum() == totalAmount) {
            val oldVal = counts.getOrDefault(sequence.size, 0)
            counts[sequence.size] = oldVal + 1
        }
    }
    return counts[counts.keys.min()]!!
}

fun main() {
    val input = parse(readInput("Day17"))
    println("Part 1: ${part1(input, 150)}")

    println("Part 2: ${part2(input, 150)}")
}

class Tests {
    val testInput = """20
        |15
        |10
        |5
        |5
    """.trimMargin()
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {
            assertEquals(4, part1(parsed, 25))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
            assertEquals(3, part2(parsed, 25))
        }

    }

}
