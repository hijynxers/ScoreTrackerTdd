package com.grapevineindustries.scoretrackertdd.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameViewModel {
    private val _exitGameDialogState = MutableStateFlow(false)
    val exitGameDialogState: StateFlow<Boolean> = _exitGameDialogState
    private val _wildCard = MutableStateFlow(3)
    val wildCard: StateFlow<Int> = _wildCard

    fun updateExitGameDialogState(state: Boolean) {
        _exitGameDialogState.update { state }
    }

    fun incrementWildCard() {
        _wildCard.update { _wildCard.value + 1 }
    }

    fun reset() {
        _exitGameDialogState.update { false }
    }
}