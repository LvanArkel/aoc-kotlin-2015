package day13

import permutations
import readInput
import kotlin.test.assertEquals

typealias Input = Pair<HashSet<String>, HashMap<Pair<String, String>, Int>>

fun parse(raw: String): Input {
    val pattern = Regex("""(\w+) would (\w+) (\d+) happiness units by sitting next to (\w+).""")
    val people = hashSetOf<String>()
    val pairings = hashMapOf<Pair<String, String>, Int>()
    raw.lines().filter(String::isNotEmpty).forEach { line ->
        val (firstPerson, arity, amountStr, secondPerson) = pattern.matchEntire(line)!!.destructured
        val amount = amountStr.toInt() * if (arity == "gain") { 1 } else { -1 }
        people.add(firstPerson)
        pairings[firstPerson to secondPerson] = amount
    }
    return people to pairings
}

fun part1(input: Input): Int {
    val (people, pairings) = input
    return permutations(people.toList()).maxOf { sequence ->
        val paddedSequence = sequence + sequence[0]
        paddedSequence.windowed(size = 2, step = 1, partialWindows = false) { (a, b) ->
            (pairings[a to b] ?: 0) + (pairings[b to a] ?: 0)
        }.sum()
    }
}

fun part2(input: Input): Int {
    val (people, pairings) = input
    val newPeople = HashSet(people + "You")
    return part1(newPeople to pairings)
}

fun main() {
    val testInput = """Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Alice would lose 2 happiness units by sitting next to David.
Bob would gain 83 happiness units by sitting next to Alice.
Bob would lose 7 happiness units by sitting next to Carol.
Bob would lose 63 happiness units by sitting next to David.
Carol would lose 62 happiness units by sitting next to Alice.
Carol would gain 60 happiness units by sitting next to Bob.
Carol would gain 55 happiness units by sitting next to David.
David would gain 46 happiness units by sitting next to Alice.
David would lose 7 happiness units by sitting next to Bob.
David would gain 41 happiness units by sitting next to Carol."""
    val parsed = parse(testInput)

    assertEquals(330, part1(parsed))

    val input = parse(readInput("Day13"))
    println("Part 1: ${part1(input)}")

//    assertEquals(-1, part2(parsed))

    println("Part 2: ${part2(input)}")
}
