package day09

import readInput
import kotlin.test.assertEquals

typealias Input = Pair<List<String>, HashMap<Pair<String, String>, Int>>

fun parse(raw: String): Input {
    val result = HashMap<Pair<String, String>, Int>()
    val locations = HashSet<String>()
    raw.lines().filter(String::isNotEmpty).forEach { line ->
        val (route, cost) = line.split(" = ")
        val (a, b) = route.split(" to ")
        val costInt = cost.toInt()
        result[a to b] = costInt
        result[b to a] = costInt
        locations.addAll(listOf(a, b))
    }
    return locations.toList() to result
}

fun permutations(locations: List<String>): Sequence<List<String>> = sequence {
    for (head in locations) {
        val tail = locations - head
        if (tail.isEmpty()) {
            yield(listOf(head))
        } else {
            for (subsequence in permutations(tail)) {
                yield(listOf(head) + subsequence)
            }
        }
    }
}

fun part1(input: Input): Int {
    val (locations, distances) = input

    return permutations(locations).map { route ->
        route.windowed(2, 1) { (start, end) ->
            distances[start to end]!!
        }.sum()
    }.min()
}

fun part2(input: Input): Int {
    val (locations, distances) = input

    return permutations(locations).map { route ->
        route.windowed(2, 1) { (start, end) ->
            distances[start to end]!!
        }.sum()
    }.max()
}

fun main() {
    val testInput = """London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141"""
    val parsed = parse(testInput)

    assertEquals(605, part1(parsed))

    val input = parse(readInput("Day09"))
    println("Part 1: ${part1(input)}")

    assertEquals(982, part2(parsed))

    println("Part 2: ${part2(input)}")
}
