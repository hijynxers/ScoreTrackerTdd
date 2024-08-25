package com.grapevineindustries.scoretrackertdd.viewmodel

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FiveCrownsViewModelTests {
    @Test
    fun alert_dialog_state_updates() {
        val vm = FiveCrownsViewModel()
        assertFalse(vm.state.isExitGameDialogShowing)
        vm.updateExitGameDialogState(true)
        assertTrue(vm.state.isExitGameDialogShowing)
        vm.updateExitGameDialogState(false)
        assertFalse(vm.state.isExitGameDialogShowing)
    }

    @Test
    fun increment_wild_card() {
        val vm = FiveCrownsViewModel()
        assert(3 == vm.state.wildCard)
        vm.incrementWildCard()
        assert(4 == vm.state.wildCard)
    }

    @Test
    fun increment_dealer() {
        val vm = FiveCrownsViewModel()
        assert(0 == vm.state.dealer)
        vm.incrementDealer()
        assert(1 == vm.state.dealer)
    }

    @Test
    fun endgame() {
        val vm = FiveCrownsViewModel()
        assertFalse(vm.endgameCondition())
        while (vm.state.wildCard != 13) {
            vm.incrementWildCard()
        }
        assertTrue(vm.endgameCondition())
    }

    @Test
    fun reset() {
        val vm = FiveCrownsViewModel()
        vm.updateExitGameDialogState(true)
        vm.incrementWildCard()
        assertTrue(vm.state.isExitGameDialogShowing)
        assert(vm.state.wildCard == 4)

        vm.reset()
        assertFalse(vm.state.isExitGameDialogShowing)
        assert(vm.state.wildCard == 3)
    }
}