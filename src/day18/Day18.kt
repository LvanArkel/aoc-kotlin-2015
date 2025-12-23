package day18

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.streams.toList
import kotlin.test.assertEquals


typealias Input = List<List<Boolean>>

fun parse(raw: String): Input {
    return raw.lines().filter(String::isNotEmpty).map { line ->
        line.chars().toList().map { it == '#'.code }
    }
}

fun toField(input: Input): Set<Pair<Int, Int>> {
    return input.withIndex().flatMap { (y, row) ->
        row.withIndex().filter { (_, v) -> v }.map { (x, _) -> x to y }
    }.toSet()
}

fun neighbour(coord: Pair<Int, Int>, width: Int, height: Int): Set<Pair<Int, Int>> {
    return (-1..1).flatMap { dx ->
        (-1..1).map { dy -> coord.first + dx to coord.second + dy }
    }
        .filter { (x, y) -> x in 0..<width && y in 0..<height }
        .toSet()
}

fun allNeighbours(field: Set<Pair<Int, Int>>, width: Int, height: Int): Set<Pair<Int, Int>> {
    return field.flatMap { neighbour(it, width, height) }.toSet()
}

fun part1(input: Input, steps: Int): Int {
    var field = toField(input)
    val width = input[0].size
    val height = input.size
    for (_i in 1..steps) {
        field = allNeighbours(field, width, height).filter { coord ->
            val count = neighbour(coord, width, height).intersect(field).size
            if (coord in field) {
                count == 3 || count == 4
            } else {
                count == 3
            }
        }.toSet()
    }
    return field.size
}

fun corners(width: Int, height: Int): Set<Pair<Int, Int>> {
    return setOf(0 to 0, width - 1 to 0, 0 to height-1, width-1 to height-1)
}

fun part2(input: Input, steps: Int): Int {
    var field = toField(input)
    val width = input[0].size
    val height = input.size
    val corners =corners(width, height)
    field = field.union(corners)
    for (_i in 1..steps) {
        field = allNeighbours(field, width, height).filter { coord ->
            val count = neighbour(coord, width, height).intersect(field).size
            if (coord in field) {
                count == 3 || count == 4
            } else {
                count == 3
            }
        }.toSet()
        field = field.union(corners)
    }
    return field.size
}

fun main() {
    val input = parse(readInput("Day18"))
    println("Part 1: ${part1(input, 100)}")

    println("Part 2: ${part2(input, 100)}")
}

class Tests {
    val testInput = """.#.#.#
...##.
#....#
..#...
#.#..#
####.."""
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testToField(){
            val input = """.#....
                |......
                |......
                |......
                |...#..
                |......
            """.trimMargin()
            assertEquals(setOf(1 to 0, 3 to 4), toField(parse(input)))
        }

        @Test
        fun testNeighbour() {
            assertEquals(setOf(0 to 0, 0 to 1, 1 to 0, 1 to 1, 2 to 0, 2 to 1), neighbour(1 to 0, 6, 6))
        }

        @Test
        fun testAllNeighbours() {
            val field = setOf(1 to 0, 4 to 4)
            assertEquals(15, allNeighbours(field, 6, 6).size)
        }

        @Test
        fun testPart1() {
            assertEquals(4, part1(parsed, 4))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
            assertEquals(17, part2(parsed, 5))
        }

    }

}
