package day12

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull
import readInput
import kotlin.test.assertEquals

typealias Input = JsonElement

fun parse(raw: String): Input {
    return Json.parseToJsonElement(raw)
}

fun part1(input: Input): Int {
    return when (input) {
        is JsonArray -> input.sumOf { part1(it) }
        is JsonObject -> input.values.sumOf { part1(it) }
        is JsonPrimitive -> input.intOrNull ?: 0
        JsonNull -> 0
    }
}

fun part2(input: Input): Int {
    return when (input) {
        is JsonArray -> input.sumOf { part2(it) }
        is JsonObject -> if (input.values.contains(JsonPrimitive("red"))) {
            0
        } else {
            input.values.sumOf { part2(it) }
        }
        is JsonPrimitive -> input.intOrNull ?: 0
        JsonNull -> 0
    }
}

fun main() {

    assertEquals(6, part1(parse("""[1,2,3]""")))
    assertEquals(6, part1(parse("""{"a":2,"b":4}""")))
    assertEquals(3, part1(parse("""[[[3]]]""")))
    assertEquals(3, part1(parse("""{"a":{"b":4},"c":-1}""")))
    assertEquals(0, part1(parse("""{"a":[-1,1]}""")))
    assertEquals(0, part1(parse("""[-1,{"a":1}]""")))
    assertEquals(0, part1(parse("""[]""")))
    assertEquals(0, part1(parse("""{}""")))

    val input = parse(readInput("Day12"))
    println("Part 1: ${part1(input)}")

    assertEquals(6, part2(parse("""[1,2,3]""")))
    assertEquals(4, part2(parse("""[1,{"c":"red","b":2},3]""")))
    assertEquals(0, part2(parse("""{"d":"red","e":[1,2,3,4],"f":5}""")))
    assertEquals(6, part2(parse("""[1,"red",5]""")))

    println("Part 2: ${part2(input)}")
}
