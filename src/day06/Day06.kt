package day06

import readInput
import kotlin.math.max
import kotlin.test.assertEquals
import kotlin.text.Regex

enum class Operation {
    TurnOn,
    TurnOff,
    Toggle,
}

typealias Input = List<Pair<Operation, List<Int>>>


val pattern = Regex("""(.+) (\d+),(\d+) through (\d+),(\d+)""")

fun parse(raw: String): Input {
    return raw.lines().map { line ->
        val match = pattern.matchEntire(line) ?: error("Invalid input: ($line)")
        val operation = when (match.groupValues[1]) {
            "turn on" -> Operation.TurnOn
            "turn off" -> Operation.TurnOff
            "toggle" -> Operation.Toggle
            else -> error("Unknown op ${match.groupValues[1]}")
        }
        val coords = match.groupValues.drop(2).map { it.toInt() }
        operation to coords
    }
}

fun part1(input: Input): Int {
    val grid = Array(1000) { Array(1000) { false     } }
    for ((operation, coords) in input) {
        val fn: (Boolean) -> Boolean = when (operation) {
            Operation.TurnOn -> { _ -> true }
            Operation.TurnOff -> { _ -> false }
            Operation.Toggle -> { light -> !light }
        }
        val (x0, y0, x1, y1) = coords
        for (x in x0..x1) {
            for (y in y0..y1) {
                grid[x][y] = fn(grid[x][y])
            }
        }
    }
    return grid.sumOf { line -> line.count { it } }
}

fun part2(input: Input): Int {
    val grid = Array(1000) { Array(1000) { 0 } }
    for ((operation, coords) in input) {
        val fn: (Int) -> Int = when (operation) {
            Operation.TurnOn -> { x -> x + 1 }
            Operation.TurnOff -> { x -> max(0, x - 1) }
            Operation.Toggle -> { x -> x + 2 }
        }
        val (x0, y0, x1, y1) = coords
        for (x in x0..x1) {
            for (y in y0..y1) {
                grid[x][y] = fn(grid[x][y])
            }
        }
    }
    return grid.sumOf { line -> line.sum() }
}

fun main() {
    val testInput = """turn on 0,0 through 999,999
        |toggle 0,0 through 999,0
        |turn off 499,499 through 500,500
    """.trimMargin()
    val parsed = parse(testInput)

    assertEquals(998_996, part1(parsed))

    val input = parse(readInput("Day06"))
    println("Part 1: ${part1(input)}")

//    assertEquals(-1, part2(parsed))

    println("Part 2: ${part2(input)}")
}
