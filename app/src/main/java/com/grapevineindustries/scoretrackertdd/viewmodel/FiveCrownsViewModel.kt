package com.grapevineindustries.scoretrackertdd.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FiveCrownsViewModel {
    private val _exitGameDialogState = MutableStateFlow(false)
    val exitGameDialogState: StateFlow<Boolean> = _exitGameDialogState
    private val _wildCard = MutableStateFlow(3)
    val wildCard: StateFlow<Int> = _wildCard
    private val _dealer = MutableStateFlow(0)
    val dealer: StateFlow<Int> = _dealer

    fun updateExitGameDialogState(state: Boolean) {
        _exitGameDialogState.update { state }
    }

    fun incrementWildCard() {
        _wildCard.update { _wildCard.value + 1 }
    }

    fun incrementDealer() {
        _dealer.update { _dealer.value + 1 }
    }

    fun reset() {
        _exitGameDialogState.update { false }
        _wildCard.update { 3 }
    }

    fun endgameCondition(): Boolean {
        return _wildCard.value == 13
    }
}