import kotlin.test.assertEquals

typealias Input = List<String>

fun main() {
    fun part1(input: Input): Int {
        return input.size
    }

    fun part2(input: Input): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    assertEquals(1, part1(listOf("test_input")))

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInputLines("Day01_test")
//    assertEquals(1, part1(testInput))

    // Read the input from the `src/Day01.txt` file.
    val input = readInputLines("Day01")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
