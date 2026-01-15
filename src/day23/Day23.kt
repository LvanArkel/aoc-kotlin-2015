package day23

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.test.assertEquals
import kotlin.time.Clock

sealed interface Operation {
    data class Half(val index: Int) : Operation
    data class Triple(val index: Int) : Operation
    data class Increment(val index: Int) : Operation
    data class Jump(val size: Int) : Operation
    data class JumpEven(val index: Int, val size: Int) : Operation
    data class JumpOne(val index: Int, val size: Int) : Operation
}

typealias Input = List<Operation>

typealias Output = List<Int>

private fun parseRegister(string: String): Int {
    return when (string) {
        "a" -> 0
        "b" -> 1
        else -> error("Unknown register $string")
    }
}

fun parse(raw: String): Input {
    return raw.lines().filter(String::isNotEmpty).map { line ->
        if (line.startsWith("hlf")) {
            Operation.Half(parseRegister(line.drop(4)))
        } else if (line.startsWith("tpl")) {
            Operation.Triple(parseRegister(line.drop(4)))
        } else if (line.startsWith("inc")) {
            Operation.Increment(parseRegister(line.drop(4)))
        } else if (line.startsWith("jmp")) {
            Operation.Jump(line.drop(4).dropWhile { it == '+' }.toInt())
        } else if (line.startsWith("jie")) {
            val splitted = line.drop(4).split(", ")
            Operation.JumpEven(
                parseRegister(splitted[0]),
                splitted[1].dropWhile { it == '+' }.toInt()
            )
        } else if (line.startsWith("jio")) {
            val splitted = line.drop(4).split(", ")
            Operation.JumpOne(
                parseRegister(splitted[0]),
                splitted[1].dropWhile { it == '+' }.toInt()
            )
        } else {
            error("Could not parse line $line")
        }
    }
}

fun createLabels(operations: List<Operation>): List<Int> {
    return operations.withIndex().mapNotNull { (i, operation) ->
        when (operation) {
            is Operation.Jump -> i + operation.size
            is Operation.JumpEven -> i + operation.size
            is Operation.JumpOne -> i + operation.size
            else -> null
        }
    }.distinct().sorted()
}

fun runProgram(input: Input, registers: MutableList<Int>): Output{
    val labels = createLabels(input)
    println(labels)

    var programCounter = 0
    while (programCounter < input.size) {
        val op = input[programCounter]
        when (op) {
            is Operation.Half -> registers[op.index] /= 2
            is Operation.Triple -> registers[op.index] *= 3
            is Operation.Increment -> registers[op.index] += 1
            is Operation.Jump -> {
                programCounter += op.size
                continue
            }
            is Operation.JumpEven -> {
                if (registers[op.index] % 2 == 0) {
                    programCounter += op.size
                    continue
                }
            }
            is Operation.JumpOne -> {
                if (registers[op.index] == 1) {
                    programCounter += op.size
                    continue
                }
            }
        }
        programCounter++
    }
    return registers
}

fun part1(input: Input): Output {
    return runProgram(input, mutableListOf(0, 0))
}

fun part2(input: Input): Output {
    return runProgram(input, mutableListOf(1, 0))
}

fun main() {
    val input = parse(readInput("Day23"))

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
    val testInput = """inc a
jio a, +2
tpl a
inc a"""
    val parsed = parse(testInput)

    @Nested
    inner class Part1 {
        @Test
        fun testPart1() {
            assertEquals(listOf(2, 0), part1(parsed))
        }
    }

    @Nested
    inner class Part2 {
//        @Test
//        fun testPart2() {
//            assertEquals(-1, part2(parsed))
//        }

    }

}
