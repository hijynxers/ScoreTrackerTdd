package com.grapevineindustries.scoretrackertdd.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class FiveCrownsState(
    val wildCard: Int = 3,
    val dealer: Int = 0,
    val isExitGameDialogShowing: Boolean = false,
    val isCalcDialogShowing: Boolean = false,
    val isNoScoreDialogShowing: Boolean = false
)

class FiveCrownsViewModel {
    private var _state: MutableStateFlow<FiveCrownsState>
    var state: StateFlow<FiveCrownsState>

    init {
        _state = MutableStateFlow(FiveCrownsState())
        state = _state
    }

    fun updateExitGameDialogState(state: Boolean) {
        _state.update { _state.value.copy(isExitGameDialogShowing = state) }
    }

    fun updateCalcDialogState(state: Boolean) {
        _state.update { _state.value.copy(isCalcDialogShowing = state) }
    }

    fun incrementWildCard() {
        _state.update { _state.value.copy(wildCard = _state.value.wildCard + 1) }
    }

    fun incrementDealer() {
        _state.update { _state.value.copy(dealer = _state.value.dealer + 1) }
    }

    fun endgameCondition(): Boolean = _state.value.wildCard == 13
}