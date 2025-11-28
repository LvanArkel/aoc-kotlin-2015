package day01

import readInput

typealias Input = String

fun main() {
    fun part1(input: Input): Int {
        return input.count { it == '(' } - input.count { it == ')' }
    }

    fun part2(input: Input): Int {
        input.foldIndexed(0, { i, floor, c ->
            val nextFloor = when (c) {
                '(' -> floor + 1
                ')' -> floor - 1
                else -> floor
            }
            if (nextFloor == -1) return i + 1
            nextFloor
        })
        throw IllegalStateException("No solution")
    }


    // Test if implementation meets criteria from the description, like:
    check(part1("(())") == 0)
    check(part1("()()") == 0)
    check(part1("(((") == 3)
    check(part1("(()(()(") == 3)
    check(part1("))(((((") == 3)
    check(part1("())") == -1)
    check(part1("))(") == -1)
    check(part1(")))") == -3)
    check(part1(")())())") == -3)

    check(part2(")") == 1)
    check(part2("()())") == 5)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInputLines("Day01_test")
//    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
