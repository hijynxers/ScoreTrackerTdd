package com.grapevineindustries.fivecrowns.viewmodel

import com.grapevineindustries.fivecrowns.FiveCrownsViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FiveCrownsViewModelTests {
    private fun createVm() = FiveCrownsViewModel()

    @Test
    fun alert_dialog_state_updates() {
        val vm = createVm()
        assertFalse(vm.state.value.isExitGameDialogShowing)
        vm.updateExitGameDialogState(true)
        assertTrue(vm.state.value.isExitGameDialogShowing)
        vm.updateExitGameDialogState(false)
        assertFalse(vm.state.value.isExitGameDialogShowing)
    }

    @Test
    fun increment_wild_card() {
        val vm = createVm()
        assert(3 == vm.state.value.wildCard)
        vm.incrementWildCard()
        assert(4 == vm.state.value.wildCard)
    }

    @Test
    fun increment_dealer() {
        val vm = createVm()
        assert(0 == vm.state.value.dealer)
        vm.incrementDealer()
        assert(1 == vm.state.value.dealer)
    }

    @Test
    fun endgame() {
        val vm = createVm()
        assertFalse(vm.endgameCondition())
        while (vm.state.value.wildCard != 13) {
            vm.incrementWildCard()
        }
        assertTrue(vm.endgameCondition())
    }
}