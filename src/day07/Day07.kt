package day07

import readInput
import kotlin.test.assertEquals

sealed interface ValueType {
    data class Constant(val v: UShort) : ValueType
    data class Variable(val name: String) : ValueType

    companion object {
        fun parse(input: String): ValueType {
            return input.toUShortOrNull()?.let {
                Constant(it)
            }?: Variable(input)
        }
    }
}

sealed interface Operation {
    data class Value(val value: ValueType) : Operation
    data class And(val left: ValueType, val right: ValueType) : Operation
    data class Or(val left: ValueType, val right: ValueType) : Operation
    data class Not(val param: ValueType) : Operation
    data class LShift(val left: ValueType, val amount: Int) : Operation
    data class RShift(val left: ValueType, val amount: Int) : Operation

    companion object {
        val andRegex = Regex(".+ AND .+")
        val orRegex = Regex(".+ OR .+")
        val notRegex = Regex("NOT .+")
        val lShiftRegex = Regex(".+ LSHIFT (\\d+)")
        val rShiftRegex = Regex(".+ RSHIFT (\\d+)")

        fun parse(input: String): Operation {
            return when {
                input.matches(andRegex) -> {
                    val (left, right) = input.split(" AND ")
                    And(ValueType.parse(left), ValueType.parse(right))
                }
                input.matches(orRegex) -> {
                    val (left, right) = input.split(" OR ")
                    Or(ValueType.parse(left), ValueType.parse(right))
                }
                input.matches(notRegex) -> {
                    val value = input.drop(4)
                    Not(ValueType.parse(value))
                }
                input.matches(lShiftRegex) -> {
                    val (left, right) = input.split(" LSHIFT ")
                    LShift(ValueType.parse(left), right.toInt())
                }
                input.matches(rShiftRegex) -> {
                    val (left, right) = input.split(" RSHIFT ")
                    RShift(ValueType.parse(left), right.toInt())
                }
                else -> Value(ValueType.parse(input))
            }
        }
    }
}

typealias Input = Map<String, Operation>

fun parse(raw: String): Input {

    return raw.lines().associate { line ->
        val splitted = line.split(" -> ")
        val op = Operation.parse(splitted[0])
        val result = splitted[1]
        result to op
    }
}

fun lookupSignal(signal: String, signals: Input, cache: HashMap<String, UShort>): UShort {
    fun resolveValue(value: ValueType): UShort {
        return when (value) {
            is ValueType.Constant -> value.v
            is ValueType.Variable -> lookupSignal(value.name, signals, cache)
        }
    }

    cache[signal]?.let { return it }
    val operation = signals[signal] ?: error("Unknown signal: $signal")
    val result: UShort = when (operation) {
        is Operation.And -> {
            val left = resolveValue(operation.left)
            val right = resolveValue(operation.right)
            left.and(right)
        }
        is Operation.Or -> {
            val left = resolveValue(operation.left)
            val right = resolveValue(operation.right)
            left.or(right)
        }
        is Operation.Not -> {
            val param = resolveValue(operation.param)
            param.inv()
        }
        is Operation.LShift -> {
            val left = resolveValue(operation.left)
            left.times((1 shl operation.amount).toUShort()).toUShort()
        }
        is Operation.RShift -> {
            val left = resolveValue(operation.left)
            left.div((1 shl operation.amount).toUShort()).toUShort()
        }
        is Operation.Value -> resolveValue(operation.value)
    }
    cache[signal] = result
    return result
}

fun part1(input: Input, signal: String): UShort {
    return lookupSignal(signal, input, HashMap())
}

fun part2(input: Input): UShort {
    val a = part1(input, "a")
    val cache: HashMap<String, UShort> = HashMap()
    cache["b"] = a
    return lookupSignal("a", input, cache)
}

fun main() {
    val testInput = """123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i"""
    val parsed = parse(testInput)

    assertEquals(123, part1(parsed, "x").toInt())
    assertEquals(456, part1(parsed, "y").toInt())
    assertEquals(72, part1(parsed, "d").toInt())
    assertEquals(507, part1(parsed, "e").toInt())
    assertEquals(492, part1(parsed, "f").toInt())
    assertEquals(114, part1(parsed, "g").toInt())
    assertEquals(65412, part1(parsed, "h").toInt())
    assertEquals(65079, part1(parsed, "i").toInt())

    val input = parse(readInput("Day07"))
    println("Part 1: ${part1(input, "a")}")

//    assertEquals(-1, part2(parsed).toInt())

    println("Part 2: ${part2(input)}")
}
