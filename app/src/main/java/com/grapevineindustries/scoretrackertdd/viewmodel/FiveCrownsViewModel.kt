package com.grapevineindustries.scoretrackertdd.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class FiveCrownsState(
    val wildCard: Int = 3,
    val dealer: Int = 0,
    val isExitGameDialogShowing: Boolean = false,
    val isCalcDialogShowing: Boolean = false
)

class FiveCrownsViewModel {
    var state by mutableStateOf(FiveCrownsState())
        private set

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

    fun reset() {
        state = state.copy(
            wildCard = 3,
            isExitGameDialogShowing = false
        )
    }

    fun endgameCondition(): Boolean = state.wildCard == 13
}