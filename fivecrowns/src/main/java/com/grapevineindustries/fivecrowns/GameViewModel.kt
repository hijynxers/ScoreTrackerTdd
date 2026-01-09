package com.grapevineindustries.fivecrowns

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {

    private var _state = MutableStateFlow(FiveCrownsState())
    var state: StateFlow<FiveCrownsState> = _state

    var players = mutableStateListOf<Player>()

    fun updateExitGameDialogState(state: Boolean) {
        _state.update { _state.value.copy(isExitGameDialogShowing = state) }
    }

    fun updateCalcDialogState(state: Boolean) {
        _state.update { _state.value.copy(isCalcDialogShowing = state) }
    }

    fun updateNoScoreDialogState(state: Boolean) {
        _state.update { _state.value.copy(isNoScoreDialogShowing = state) }
    }

    fun incrementWildCard() {
        _state.update { _state.value.copy(wildCard = _state.value.wildCard + 1) }
    }

    fun incrementDealer() {
        _state.update { _state.value.copy(dealer = _state.value.dealer + 1) }
    }

    fun endgameCondition(): Boolean = _state.value.wildCard == 13

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

        var hasScore = false
        tempList.forEach { player ->
            if (player.pendingPoints > 0 )
                hasScore = true
        }
        if (hasScore) {
            tempList.forEachIndexed { index, _ ->
                val score = players[index].pendingPoints + players[index].score
                players[index] = players[index].copy(
                    score = score,
                    pendingPoints = 0
                )
            }
        } else {
            updateNoScoreDialogState(true)
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

    fun resetWildCard() {
        _state.update { FiveCrownsState() }
    }

    fun resetGameAndPlayers() {
        this.players.clear()
        this.resetScores()
        this.resetWildCard()
    }
}

data class FiveCrownsState(
    val wildCard: Int = 3,
    val dealer: Int = 0,
    val isExitGameDialogShowing: Boolean = false,
    val isCalcDialogShowing: Boolean = false,
    val isNoScoreDialogShowing: Boolean = false
)