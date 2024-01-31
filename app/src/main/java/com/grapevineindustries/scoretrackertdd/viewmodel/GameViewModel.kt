package com.grapevineindustries.scoretrackertdd.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameViewModel {
    private val _exitGameDialogState = MutableStateFlow(false)
    val exitGameDialogState: StateFlow<Boolean> = _exitGameDialogState

    fun updateExitGameDialogState(state: Boolean) {
        _exitGameDialogState.update { state }
    }

    fun reset() {
        _exitGameDialogState.update { false }
    }
}