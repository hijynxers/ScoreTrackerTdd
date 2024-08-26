package com.grapevineindustries.scoretrackertdd.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class FiveCrownsState(
    val wildCard: Int = 3,
    val dealer: Int = 0,
    val isExitGameDialogShowing: Boolean = false,
    val isCalcDialogShowing: Boolean = false
)

class FiveCrownsViewModel {
    var playerList: MutableList<Player> = mutableListOf()

    var state by mutableStateOf(FiveCrownsState())
        private set

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

    private fun resetScores() {
        val tempList = playerList.toList()
        tempList.forEachIndexed { index, _ ->
            playerList[index] = playerList[index].copy(
                score = 0,
                pendingPoints = 0
            )
        }
    }








    fun updateExitGameDialogState(state: Boolean) {
        this.state = this.state.copy(isExitGameDialogShowing = state)
    }

    fun updateCalcDialogState(state: Boolean) {
        this.state = this.state.copy(isCalcDialogShowing = state)
    }

    fun incrementWildCard() {
        state = state.copy(wildCard = state.wildCard + 1)
    }

    fun incrementDealer() {
        state = state.copy(dealer = state.dealer + 1)
    }

    fun resetNewGame() {
        resetScores()
        state = FiveCrownsState(dealer = state.dealer)
    }

    fun endgameCondition(): Boolean = state.wildCard == 13
}