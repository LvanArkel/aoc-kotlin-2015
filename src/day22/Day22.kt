package day22

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals
import kotlin.time.Clock

data class Boss(
    val health: Int,
    val attack: Int,
)

data class Player(
    val health: Int,
    val mana: Int,
)

data class CombatState(
    val playerHealth: Int,
    val bossHealth: Int,

    val mana: Int,
    val shieldTimer: Int,
    val poisonTimer: Int,
    val rechargeTimer: Int,

    val manaSpent: Int,
)

sealed class Move(val requirement: Int) {
    object MagicMissile : Move(53)
    object Drain : Move(73)
    object Shield : Move(113)
    object Poison : Move(173)
    object Recharge : Move(229)
}

fun availableMoves(state: CombatState): List<Move> {
    val moves: MutableList<Move> = mutableListOf()
    val manaAfterRecharge = if (state.rechargeTimer > 0) 101 + state.mana else state.mana
    if (manaAfterRecharge >= Move.MagicMissile.requirement) moves += Move.MagicMissile
    if (manaAfterRecharge >= Move.Drain.requirement) moves += Move.Drain
    if (
        manaAfterRecharge >= Move.Shield.requirement &&
        state.shieldTimer <= 1
    ) moves += Move.Shield
    if (
        manaAfterRecharge >= Move.Poison.requirement &&
        state.poisonTimer <= 1
    ) moves += Move.Poison
    if (
        manaAfterRecharge >= Move.Recharge.requirement &&
        state.rechargeTimer <= 1
    ) moves += Move.Recharge
    return moves
}

fun applyPlayerTurn(state: CombatState, move: Move, hardMode: Boolean): CombatState {
    val healing = if (move is Move.Drain) 2 else 0
    val attack = when (move) {
        is Move.MagicMissile -> 4
        is Move.Drain -> 2
        else -> 0
    }
    val poisonAttack = if (state.poisonTimer > 0) 3 else 0
    val manaRecharge = if (state.rechargeTimer > 0) 101 else 0
    val hardDamage = if (hardMode) 1 else 0
    return CombatState(
        playerHealth = state.playerHealth + healing - hardDamage,
        bossHealth = state.bossHealth - attack - poisonAttack,
        mana = state.mana + manaRecharge - move.requirement,
        shieldTimer = if (move is Move.Shield) 6 else max(0, state.shieldTimer - 1),
        poisonTimer = if (move is Move.Poison) 6 else max(0, state.poisonTimer - 1),
        rechargeTimer = if (move is Move.Recharge) 5 else max(0, state.rechargeTimer - 1),
        manaSpent = state.manaSpent + move.requirement
    )
}

fun applyBossTurn(state: CombatState, bossAttack: Int): CombatState {
    val armor = if (state.shieldTimer > 0) 7 else 0
    val bossDamage = max(1, bossAttack - armor)
    val poisonAttack = if (state.poisonTimer > 0) 3 else 0
    val manaRecharge = if (state.rechargeTimer > 0) 101 else 0
    return CombatState(
        playerHealth = state.playerHealth - bossDamage,
        bossHealth = state.bossHealth - poisonAttack,
        mana = state.mana + manaRecharge,
        shieldTimer = max(0, state.shieldTimer - 1),
        poisonTimer = max(0, state.poisonTimer - 1),
        rechargeTimer = max(0, state.rechargeTimer - 1),
        manaSpent = state.manaSpent
    )
}

fun applyTurn(state: CombatState, move: Move, bossAttack: Int, hardMode: Boolean = false): CombatState{
    val playerTurn = applyPlayerTurn(state, move, hardMode)
    val bossTurn = applyBossTurn(playerTurn, bossAttack)
    return bossTurn
}

fun possibleTurns(state: CombatState, bossAttack: Int, hardMode: Boolean): List<Pair<Move, CombatState>> {
    return availableMoves(state).map { move -> move to applyTurn(state, move, bossAttack, hardMode) }
}

fun isFinished(state: CombatState): Boolean? {
    if (state.bossHealth <= 0) return true
    if (state.playerHealth <= 0) return false
    return null
}

fun part1(boss: Boss, hardMode: Boolean = false): Int {
    val player = Player(health = 50, mana = 500)
    val initialState = CombatState(
        playerHealth = player.health,
        bossHealth = boss.health,
        mana = player.mana,
        shieldTimer = 0,
        poisonTimer = 0,
        rechargeTimer = 0,
        manaSpent = 0,
    )
    val states = mutableListOf(initialState)
    var minimalWinMana = Int.MAX_VALUE
    while (states.isNotEmpty()) {
        val state = states.removeFirst()

        val newStates = possibleTurns(state, boss.attack, hardMode)

        for (turn in newStates) {
            val newState = turn.second
            when (isFinished(newState)) {
                true -> {
                    minimalWinMana = min(minimalWinMana, newState.manaSpent)
                }
                false -> {}
                null -> {
                    states.add(newState)
                }
            }
        }
    }
    return minimalWinMana
}

fun part2(boss: Boss): Int {
    return part1(boss, hardMode = true)
}

fun main() {
    val input = Boss(health = 71, attack = 10)

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
        fun testFirstExample() {
            val bossAttack = 8
            val initialState = CombatState(
                playerHealth = 10,
                bossHealth = 13,
                mana = 250,
                shieldTimer = 0,
                poisonTimer = 0,
                rechargeTimer = 0,
                manaSpent = 0
            )
            val firstTurn = applyTurn(initialState, Move.Poison, bossAttack)
            assertEquals(2, firstTurn.playerHealth)
            assertEquals(10, firstTurn.bossHealth)
            assertEquals(77, firstTurn.mana)
            assertEquals(5, firstTurn.poisonTimer)
            val finalTurn = applyTurn(firstTurn, Move.MagicMissile, bossAttack)
            assertEquals(true, isFinished(finalTurn))
            assertEquals(24, finalTurn.mana)
        }

        @Test
        fun testSecondExample() {
            val bossAttack = 8
            val initialState = CombatState(
                playerHealth = 10,
                bossHealth = 14,
                mana = 250,
                shieldTimer = 0,
                poisonTimer = 0,
                rechargeTimer = 0,
                manaSpent = 0
            )
            val turn1 = applyTurn(initialState, Move.Recharge, bossAttack)
            assertEquals(2, turn1.playerHealth)
            assertEquals(14, turn1.bossHealth)
            assertEquals(122, turn1.mana)
            assertEquals(4, turn1.rechargeTimer)
            val turn2 = applyTurn(turn1, Move.Shield, bossAttack)
            assertEquals(1, turn2.playerHealth)
            assertEquals(14, turn2.bossHealth)
            assertEquals(211, turn2.mana)
            assertEquals(2, turn2.rechargeTimer)
            assertEquals(5, turn2.shieldTimer)
            val turn3 = applyTurn(turn2, Move.Drain, bossAttack)
            assertEquals(2, turn3.playerHealth)
            assertEquals(12, turn3.bossHealth)
            assertEquals(340, turn3.mana)
            assertEquals(0, turn3.rechargeTimer)
            assertEquals(3, turn3.shieldTimer)
            val turn4 = applyTurn(turn3, Move.Poison, bossAttack)
            assertEquals(1, turn4.playerHealth)
            assertEquals(9, turn4.bossHealth)
            assertEquals(167, turn4.mana)
            assertEquals(1, turn4.shieldTimer)
            assertEquals(5, turn4.poisonTimer)
            val turn5 = applyTurn(turn4, Move.MagicMissile, 90)
            assertEquals(true, isFinished(turn5))
            assertEquals(114, turn5.mana)
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
