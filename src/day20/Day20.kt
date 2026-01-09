package day20

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

typealias Input = Int

// S = n*(n+1) / 2

// S = (n^2 + n) / 2

// 2S = n^2+n

// n^2+n - 2S = 0
//a=1, b=1, c=-2S
//n = (-1 + sqrt(1+8S)) / 2

fun part1(minPresents: Input): Int {
    val nMax = 1_000_000
    val numbers = Array(nMax + 1) { 0 }
    for (i in 1..nMax) {
        var j = i
        while (j <= nMax) {
            numbers[j] += i * 10
            j += i
        }
    }
    return numbers.withIndex().first { it.value >= minPresents }.index
}

fun part2(minPresents: Input): Int {
    val nMax = 1_000_000
    val numbers = Array(nMax + 1) { 0 }
    for (i in 1..nMax) {
        var j = i
        for (iteration in 1..50) {
            if (j >= numbers.size) break
            numbers[j] += i * 11
            j += i
        }
    }
    return numbers.withIndex().first { it.value >= minPresents }.index
}

fun main() {
    val start1 = Clock.System.now()
    val part1 = part1(33100000)
    val end1= Clock.System.now()
    println("Part 1: $part1 in (${end1 - start1} sec)")

    val start2 = Clock.System.now()
    val part2 = part2(33100000)
    val end2= Clock.System.now()
    println("Part 2: $part2 in (${end2 - start2} sec)")
}

class Tests {

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {

        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
//            assertEquals(-1, part2(parsed))
        }

    }

}
