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
}

data class Player(var name: String, var score: Int = 0)