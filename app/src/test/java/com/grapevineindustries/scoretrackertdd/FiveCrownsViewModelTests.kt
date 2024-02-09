package com.grapevineindustries.scoretrackertdd

import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FiveCrownsViewModelTests {
    @Test
    fun alert_dialog_state_updates() {
        val vm = FiveCrownsViewModel()
        assertFalse(vm.exitGameDialogState.value)
        vm.updateExitGameDialogState(true)
        assertTrue(vm.exitGameDialogState.value)
        vm.updateExitGameDialogState(false)
        assertFalse(vm.exitGameDialogState.value)
    }

    @Test
    fun increment_wild_card() {
        val vm = FiveCrownsViewModel()
        assert(3 == vm.wildCard.value)
        vm.incrementWildCard()
        assert(4 == vm.wildCard.value)
    }

    @Test
    fun increment_dealer() {
        val vm = FiveCrownsViewModel()
        assert(0 == vm.dealer.value)
        vm.incrementDealer()
        assert(1 == vm.dealer.value)
    }

    @Test
    fun endgame() {
        val vm = FiveCrownsViewModel()
        assertFalse(vm.endgameCondition())
        while (vm.wildCard.value != 13) {
            vm.incrementWildCard()
        }
        assertTrue(vm.endgameCondition())
    }

    @Test
    fun reset() {
        val vm = FiveCrownsViewModel()
        vm.updateExitGameDialogState(true)
        vm.incrementWildCard()
        vm.incrementDealer()
        assertTrue(vm.exitGameDialogState.value)
        assert(vm.wildCard.value == 4)
        assert(vm.dealer.value == 1)

        vm.reset()
        assertFalse(vm.exitGameDialogState.value)
        assert(vm.wildCard.value == 3)
        assert(vm.dealer.value == 1)
    }
}