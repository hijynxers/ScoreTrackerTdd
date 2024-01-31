package com.grapevineindustries.scoretrackertdd

import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GameViewModelTests {
    @Test
    fun alert_dialog_state_updates() {
        val vm = GameViewModel()
        assertFalse(vm.exitGameDialogState.value)
        vm.updateExitGameDialogState(true)
        assertTrue(vm.exitGameDialogState.value)
        vm.updateExitGameDialogState(false)
        assertFalse(vm.exitGameDialogState.value)
    }

    @Test
    fun increment_wild_card() {
        val vm = GameViewModel()
        assert(3 == vm.wildCard.value)
        vm.incrementWildCard()
        assert(4 == vm.wildCard.value)
    }

    @Test
    fun reset() {
        val vm = GameViewModel()
        vm.updateExitGameDialogState(true)
        assertTrue(vm.exitGameDialogState.value)

        vm.reset()
        assertFalse(vm.exitGameDialogState.value)
    }
}