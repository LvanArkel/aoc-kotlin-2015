package day16

import readInput

typealias Input = List<Map<String, Int>>

fun parse(raw: String): Input {
    val pattern = Regex("""(\w+): (\d+)""")
    return raw.lines().filter(String::isNotEmpty).map { line ->
        pattern.findAll(line).associate { it.groupValues[1] to it.groupValues[2].toInt() }
    }
}

fun hints(): Map<String, Int> {
    return """children: 3
cats: 7
samoyeds: 2
pomeranians: 3
akitas: 0
vizslas: 0
goldfish: 5
trees: 3
cars: 2
perfumes: 1""".lines().associate { line ->
        val splitted = line.split(": ")
        splitted[0] to splitted[1].toInt()
    }
}

fun part1(input: Input): Int {
    val hints = hints()
    return input.indexOfFirst { sue ->
        hints.all { (key, value) -> sue[key]?.let { it == value } ?: true }
    } + 1
}

fun part2Eval(key: String, value: Int, sue: Map<String, Int>): Boolean {
    return sue[key]?.let { sueVal ->
        when (key) {
            "cats", "trees" -> sueVal > value
            "pomeranians", "goldfish" -> sueVal < value
            else -> sueVal == value
        }
    } ?: true
}

fun part2(input: Input): Int {
    val hints = hints()
    return input.indexOfFirst { sue ->
        hints.all { (key, value) -> part2Eval(key, value, sue) }
    } + 1
}

fun main() {
    val input = parse(readInput("Day16"))
    println("Part 1: ${part1(input)}")

    println("Part 2: ${part2(input)}")
}
