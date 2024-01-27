package com.grapevineindustries.scoretrackertdd

import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GameViewModelTests {
    @Test
    fun alert_dialog_state_updates() {
        val vm = GameViewModel()
        assertFalse(vm.backDialogState.value)
        vm.updateDialogState(true)
        assertTrue(vm.backDialogState.value)
        vm.updateDialogState(false)
        assertFalse(vm.backDialogState.value)
    }

    @Test
    fun reset() {
        val vm = GameViewModel()
        vm.updateDialogState(true)
        assertTrue(vm.backDialogState.value)

        vm.reset()
        assertFalse(vm.backDialogState.value)
    }
}