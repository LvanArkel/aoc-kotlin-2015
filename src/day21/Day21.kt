package day21

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readInput
import kotlin.math.ceil
import kotlin.math.max
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Instant

data class Item(
    val cost: Int,
    val damage: Int,
    val armor: Int,
)

data class ItemShop(
    val weapons: List<Item>,
    val armors: List<Item>,
    val rings: List<Item>,
)

data class Character(
    val health: Int,
    val damage: Int,
    val armor: Int,
)

val itemShopRaw = """Weapons:    Cost  Damage  Armor
Dagger        8     4       0
Shortsword   10     5       0
Warhammer    25     6       0
Longsword    40     7       0
Greataxe     74     8       0

Armor:      Cost  Damage  Armor
Leather      13     0       1
Chainmail    31     0       2
Splintmail   53     0       3
Bandedmail   75     0       4
Platemail   102     0       5

Rings:      Cost  Damage  Armor
Damage +1    25     1       0
Damage +2    50     2       0
Damage +3   100     3       0
Defense +1   20     0       1
Defense +2   40     0       2
Defense +3   80     0       3"""

fun parseSection(raw: String): List<Item> {
    val lineRegex = Regex(""".* (\d+)\s+(\d+)\s+(\d+)""")
    return raw.lines().drop(1).map { line ->
        val (cost, damage, armor) = lineRegex.matchEntire(line)!!.destructured
        Item(cost.toInt(), damage.toInt(), armor.toInt())
    }
}

fun parse(raw: String): ItemShop {
    val splitted = raw.split("\n\n")
    val weapons = parseSection(splitted[0])
    val armors = parseSection(splitted[1])
    val rings = parseSection(splitted[2])
    return ItemShop(weapons, armors, rings)
}

val itemShop = parse(itemShopRaw)

fun winBattle(you: Character, boss: Character): Boolean {
    val youTurns = ceil(boss.health.toFloat() / max(1, you.damage - boss.armor))
    val bossTurns = ceil(you.health.toFloat() / max(1, boss.damage - you.armor))
    return youTurns <= bossTurns
}

fun toCharacter(health: Int, items: List<Item>): Pair<Character, Int> {
    return Character(
        health = health,
        damage = items.sumOf { it.damage },
        armor = items.sumOf { it.armor }
    ) to items.sumOf { it.cost }
}

fun ringCombinations(): List<List<Item>> {
    val noRings = listOf(emptyList<Item>())
    val singleRing = itemShop.rings.map { listOf(it) }
    val doubleRings = (0 until itemShop.rings.size - 1).flatMap { a ->
        (a+1 until itemShop.rings.size).map { b -> listOf(itemShop.rings[a], itemShop.rings[b]) }
    }
    return noRings + singleRing + doubleRings
}

fun equipment(health: Int): Sequence<Pair<Character, Int>> = sequence {
    val ringOptions = ringCombinations()
    for (weapon in itemShop.weapons) {
        for (rings in ringOptions) {
            yield(toCharacter(health, listOf(weapon) + rings))
            for (armor in itemShop.armors) {
                yield(toCharacter(health, listOf(weapon, armor) + rings))
            }
        }
    }
}

fun part1(boss: Character): Int {
    return equipment(100).filter { (you, _) -> winBattle(you, boss) }.minOf { it.second }
}

fun part2(boss: Character): Int {
    return equipment(100).filter { (you, _) -> !winBattle(you, boss) }.maxOf { it.second }
}

fun main() {
    val input = Character(100, 8,2)
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

    @Nested
    inner class Part1 {
        @Test
        fun testWind() {
            assertTrue(winBattle(
                Character(8, 5, 5),
                Character(12,7, 2))
            )
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun testPart2() {
//            assertEquals(-1, part2(parsed))
        }

    }

}
