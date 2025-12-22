package day14

import readInput
import kotlin.test.assertEquals

data class Reindeer(val speed: Int, val duration: Int, val rest: Int)

typealias Input = List<Reindeer>

fun parse(raw: String): Input {
    val pattern = Regex("""\w+ can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""")
    return raw.lines().filter(String::isNotEmpty).map { line ->
        val match = pattern.matchEntire(line.trim())
        if (match == null) {
            println(line)
        }
        val (speed, duration, rest) = match!!.destructured
        Reindeer(speed.toInt(), duration.toInt(), rest.toInt())
    }
}

fun part1(input: Input, raceDuration: Int): Int {
    return input.maxOf { raindeer ->
        val cycleDuration = raindeer.duration + raindeer.rest
        val cycles = raceDuration / cycleDuration
        val remainder = raceDuration % cycleDuration
        val cycleDistance = cycles * raindeer.speed * raindeer.duration
        cycleDistance + if (remainder < raindeer.duration) {
            remainder * raindeer.speed
        } else{
            raindeer.duration * raindeer.speed
        }
    }
}

fun part2(input: Input, raceDuration: Int): Int {
    val scores = MutableList(input.size) { 0 }
    val distances = MutableList(input.size) { 0 }
    for (second in 0..raceDuration) {
        for ((rI, reindeer) in input.withIndex()) {
            val distanceUpdate = if (second % (reindeer.duration + reindeer.rest) < reindeer.duration) {
                reindeer.speed
            } else {
                0
            }
            distances[rI] += distanceUpdate
        }
        val maxDistance = distances.max()
        for (i in scores.indices) {
            if (distances[i] == maxDistance) scores[i] += 1
        }
    }
    return scores.max()
}

fun main() {
    val testInput = """
    Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
    Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
"""
    val parsed = parse(testInput)

    assertEquals(1120, part1(parsed, 1000))

    val input = parse(readInput("Day14"))
    println("Part 1: ${part1(input, 2503)}")

    assertEquals(689, part2(parsed, 1000))

    println("Part 2: ${part2(input, 2503)}")
}
