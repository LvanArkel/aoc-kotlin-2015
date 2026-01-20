package day25

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.test.assertEquals
import kotlin.time.Clock

typealias Input = Triple<Long, Int, Int>

fun coordinate_to_index(x: Int, y: Int): Int {
    val diagonal = x + y - 2
    val diagonalStart = (diagonal+1)*diagonal / 2
    return diagonalStart + x - 1
}

fun nextValue(start: Long): Long{
    return (start * 252533L) % 33554393L
}

fun part1(input: Input, debug: Boolean = false): Long {
    val (start, x, y) = input
    val i = coordinate_to_index(x, y)
    println("Repeating $i times")
    var current = start
    for (index in 0 until i) {
        current = nextValue(current)
    }
    return current
}

fun part2(input: Input, debug: Boolean = false): Int {
    return 0
}

fun main() {
    val input = Triple(20151125L, 3029, 2947)

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

    @Nested
    inner class Part1 {
        @Test
        fun testCoordinates() {
            assertEquals(0, coordinate_to_index(1, 1))
            assertEquals(1, coordinate_to_index(1, 2))
            assertEquals(2, coordinate_to_index(2, 1))
            assertEquals(3, coordinate_to_index(1, 3))
            assertEquals(4, coordinate_to_index(2, 2))
            assertEquals(5, coordinate_to_index(3, 1))
            assertEquals(6, coordinate_to_index(1, 4))
            assertEquals(7, coordinate_to_index(2, 3))
            assertEquals(8, coordinate_to_index(3, 2))
            assertEquals(9, coordinate_to_index(4, 1))
            assertEquals(10, coordinate_to_index(1, 5))
            assertEquals(11, coordinate_to_index(2, 4))
            assertEquals(12, coordinate_to_index(3, 3))
            assertEquals(13, coordinate_to_index(4, 2))
            assertEquals(14, coordinate_to_index(5, 1))
            assertEquals(15, coordinate_to_index(1, 6))
            assertEquals(16, coordinate_to_index(2, 5))
            assertEquals(17, coordinate_to_index(3, 4))
            assertEquals(18, coordinate_to_index(4, 3))
            assertEquals(19, coordinate_to_index(5, 2))
            assertEquals(20, coordinate_to_index(6, 1))


        }

        @Test
        fun testPart1() {
            assertEquals(20151125L, part1(Triple(20151125L, 1,1), debug = true))
            assertEquals(31916031L, part1(Triple(20151125L, 1,2), debug = true))
            assertEquals(16080970L, part1(Triple(20151125L, 1,3), debug = true))
            assertEquals(24592653L, part1(Triple(20151125L, 1,4), debug = true))
            assertEquals(77061L, part1(Triple(20151125L, 1,5), debug = true))
            assertEquals(33071741L, part1(Triple(20151125L, 1,6), debug = true))

            assertEquals(18749137L, part1(Triple(20151125L, 2,1), debug = true))
            assertEquals(21629792L, part1(Triple(20151125L, 2,2), debug = true))
            assertEquals(8057251L, part1(Triple(20151125L, 2,3), debug = true))
            assertEquals(32451966L, part1(Triple(20151125L, 2,4), debug = true))
            assertEquals(17552253L, part1(Triple(20151125L, 2,5), debug = true))
            assertEquals(6796745L, part1(Triple(20151125L, 2,6), debug = true))

            assertEquals(17289845L, part1(Triple(20151125L, 3,1), debug = true))
            assertEquals(16929656L, part1(Triple(20151125L, 3,2), debug = true))
            assertEquals(1601130L, part1(Triple(20151125L, 3,3), debug = true))
            assertEquals(21345942L, part1(Triple(20151125L, 3,4), debug = true))
            assertEquals(28094349L, part1(Triple(20151125L, 3,5), debug = true))
            assertEquals(25397450L, part1(Triple(20151125L, 3,6), debug = true))

            assertEquals(30943339L, part1(Triple(20151125L, 4,1), debug = true))
            assertEquals(7726640L, part1(Triple(20151125L, 4,2), debug = true))
            assertEquals(7981243L, part1(Triple(20151125L, 4,3), debug = true))
            assertEquals(9380097L, part1(Triple(20151125L, 4,4), debug = true))
            assertEquals(6899651L, part1(Triple(20151125L, 4,5), debug = true))
            assertEquals(24659492L, part1(Triple(20151125L, 4,6), debug = true))

            assertEquals(10071777L, part1(Triple(20151125L, 5,1), debug = true))
            assertEquals(15514188L, part1(Triple(20151125L, 5,2), debug = true))
            assertEquals(11661866L, part1(Triple(20151125L, 5,3), debug = true))
            assertEquals(10600672L, part1(Triple(20151125L, 5,4), debug = true))
            assertEquals(9250759L, part1(Triple(20151125L, 5,5), debug = true))
            assertEquals(1534922L, part1(Triple(20151125L, 5,6), debug = true))

            assertEquals(33511524L, part1(Triple(20151125L, 6,1), debug = true))
            assertEquals(4041754L, part1(Triple(20151125L, 6,2), debug = true))
            assertEquals(16474243L, part1(Triple(20151125L, 6,3), debug = true))
            assertEquals(31527494L, part1(Triple(20151125L, 6,4), debug = true))
            assertEquals(31663883L, part1(Triple(20151125L, 6,5), debug = true))
            assertEquals(27995004L, part1(Triple(20151125L, 6,6), debug = true))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
//            assertEquals(-1, part2(parsed, debug = true))
        }

    }

}
