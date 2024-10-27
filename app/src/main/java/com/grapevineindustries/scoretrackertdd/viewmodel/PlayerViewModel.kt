package com.grapevineindustries.scoretrackertdd.viewmodel

import androidx.compose.runtime.mutableStateListOf
import kotlinx.serialization.Serializable

class PlayerViewModel {
    var players = mutableStateListOf<Player>()

    fun createPlayersList(numPlayers: Int) {
        reset()
        for (i in 1..numPlayers) {
            players.add(Player(""))
        }
    }

    fun updatePlayer(index: Int, player: Player) {
        players[index] = player
    }

    fun setScore(index: Int, value: Int) {
        players[index] = players[index].copy(score = value)
    }

    fun setPotentialPoints(index: Int, value: Int) {
        players[index] = players[index].copy(pendingPoints = value)
    }

    fun tallyPoints() {
        val tempList = players.toList()
        tempList.forEachIndexed { index, _ ->
            val score = players[index].pendingPoints + players[index].score
            players[index] = players[index].copy(
                score = score,
                pendingPoints = 0
            )
        }
    }

    fun reset() {
        players = mutableStateListOf()
    }

    fun resetScores() {
        val tempList = players.toList()
        tempList.forEachIndexed { index, _ ->
            players[index] = players[index].copy(
                score = 0,
                pendingPoints = 0
            )
        }
    }

    fun sortedPlayers(): List<Player> {
        return players.sortedBy { it.score }
    }
}

@Serializable
data class Player(
    var name: String,
    var score: Int = 0,
    var pendingPoints: Int = 0
)