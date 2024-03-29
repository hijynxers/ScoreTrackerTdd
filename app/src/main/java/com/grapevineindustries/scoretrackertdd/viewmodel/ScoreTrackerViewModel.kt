package com.grapevineindustries.scoretrackertdd.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.grapevineindustries.scoretrackertdd.ui.GameOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ScoreTrackerViewModel {
    var playerList = mutableStateListOf<Player>()

    private val _dealer = MutableStateFlow(0)
    val dealer: StateFlow<Int> = _dealer
    private val _game = MutableStateFlow(GameOption.FIVE_CROWNS)
    val game: StateFlow<GameOption> = _game

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

    fun updateGame(game: GameOption) {
        _game.update { game }
    }

    fun incrementDealer() {
        _dealer.update { _dealer.value + 1 }
    }

    fun reset() {
        playerList = mutableStateListOf()
        _dealer.update { 0 }
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