package day15

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.math.max
import kotlin.test.assertEquals

data class Ingredient(
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int,
) {
    fun propertiesPerSpoon(spoons: Int): List<Int> =
        listOf(capacity * spoons, durability * spoons, flavor * spoons, texture * spoons)
}

typealias Input = List<Ingredient>

fun parse(raw: String): Input {
    val pattern = Regex("""\w+ (-?\d+)""")
    return raw.lines().filter(String::isNotEmpty).map { line ->
        val capture = pattern.findAll(line)
        val items = capture.map { it.groupValues[1].toInt() }.toList()
        if (items.size != 5) {
            error("Invalid items $items for line $line")
        }
        Ingredient(items[0], items[1], items[2], items[3], items[4])
    }
}

fun value(input: Input, spoonsPerItem: List<Int>): Int {
    val appliedSpoons = input.zip(spoonsPerItem).map { (ingredient, spoons) -> ingredient.propertiesPerSpoon(spoons) }
    val x = appliedSpoons[0].indices.map { i ->
        max(0, appliedSpoons.sumOf { it[i] })
    }
    return x.reduce(Int::times)
}

fun spoonSequences(totalSpoons: Int, length: Int): Sequence<List<Int>> = sequence {
    if (length <= 1) {
        yield(listOf(totalSpoons))
    } else {
        (0..totalSpoons).forEach { i ->
            for (subsequence in spoonSequences(totalSpoons - i, length - 1)) {
                yield(listOf(i) + subsequence)
            }
        }
    }
}

fun part1(input: Input): Int {
    return spoonSequences(100, input.size).maxOf {
        value(input, it)
    }
}

fun calories(input: Input, spoonsPerItem: List<Int>): Int {
    return input.zip(spoonsPerItem).sumOf { (ingredient, spoons) -> ingredient.calories * spoons }
}

fun part2(input: Input): Int {
    return spoonSequences(100, input.size).filter {
        calories(input, it) == 500
    }.maxOf {
        value(input, it)
    }
}

fun main() {
    val input = parse(readInput("Day15"))
    println("Part 1: ${part1(input)}")

    println("Part 2: ${part2(input)}")
}

class Tests {
    val testInput = """Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"""
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testValue() {
            assertEquals(62842880, value(parsed, listOf(44, 56)))
        }

        @Test
        fun testPart1() {
            assertEquals(62842880, part1(parsed))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
            assertEquals(-1, part2(parsed))
        }
    }

}
