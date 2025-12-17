package day11

import kotlin.test.assertEquals

typealias Input = String

fun increasePassword(initial: String): Sequence<List<Int>> = sequence {
    val password: MutableList<Int> = mutableListOf()
    initial.chars().forEach { password.add(it) }
    val minC = 'a'.code
    val maxC = 'z'.code
    while (true) {
        for (i in password.indices.reversed()) {
            password[i] += 1
            if (password[i] <= maxC) break
            password[i] = minC
        }
        yield(password)
    }
}

fun part1(input: Input): String {
    val pat = Regex(".*(.)\\1.*(.)\\2.*")
    return increasePassword(input).first { chars ->
        val string = chars.map { it.toChar() }.joinToString("")
        (0 until chars.size -2).any { i ->
            chars[i] + 1 == chars[i+1] &&
                    chars[i] +2 == chars[i+2]
        } &&
                !chars.contains('i'.code) &&
                !chars.contains('o'.code) &&
                !chars.contains('l'.code) &&
                pat.matches(string)
    }.map { it.toChar() }.joinToString(separator = "")
}

fun part2(input: Input): String {
    return part1(part1(input))
}

fun main() {
    assertEquals("abcdffaa", part1("abcdefgh"))
    assertEquals("ghjaabcc", part1("ghijklmn"))

    val input = "cqjxjnds"
    println("Part 1: ${part1(input)}")

    println("Part 2: ${part2(input)}")
}
