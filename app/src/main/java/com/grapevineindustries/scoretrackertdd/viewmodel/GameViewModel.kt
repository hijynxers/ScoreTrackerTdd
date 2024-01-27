package com.grapevineindustries.scoretrackertdd.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameViewModel {
    private val _backDialogState = MutableStateFlow(false)
    val backDialogState: StateFlow<Boolean> = _backDialogState

    fun updateDialogState(state: Boolean) {
        _backDialogState.update { state }
    }

    fun reset() {
        _backDialogState.update { false }
    }
}