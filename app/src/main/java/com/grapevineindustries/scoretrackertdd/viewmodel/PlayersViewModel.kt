package com.grapevineindustries.scoretrackertdd.viewmodel

import androidx.compose.runtime.mutableStateListOf

class PlayersViewModel {
    var playerList = mutableStateListOf<Player>()

    fun createPlayersList(numPlayers: Int) {
        for (i in 1..numPlayers) {
            playerList.add(Player(""))
        }
    }

    fun setName(index: Int, value: String) {
        playerList[index] = playerList[index].copy(name = value)
    }

    fun setScore(index: Int, value: Int) {
        playerList[index] = playerList[index].copy(score = value)
    }

    fun setPotentialPoints(index: Int, value: Int) {
        playerList[index] = playerList[index].copy(pendingPoints = value)
    }

    fun reset() {
        playerList = mutableStateListOf()
    }

    fun resetScores() {
        val tempList = playerList.toList()
        tempList.forEachIndexed { index, _ ->
            playerList[index] = playerList[index].copy(
                score = 0,
                pendingPoints = 0
            )
        }
    }

    fun tallyPoints() {
        val tempList = playerList.toList()
        tempList.forEachIndexed { index, _ ->
            val score = playerList[index].pendingPoints + playerList[index].score
            playerList[index] = playerList[index].copy(
                score = score,
                pendingPoints = 0
            )
        }
    }

    fun sortedPlayers(): List<Player> {
        return playerList.sortedBy { it.score }
    }
}

data class Player(
    var name: String,
    var score: Int = 0,
    var pendingPoints: Int = 0
)