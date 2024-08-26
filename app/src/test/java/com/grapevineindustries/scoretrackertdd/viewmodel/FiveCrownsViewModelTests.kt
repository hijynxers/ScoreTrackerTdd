package com.grapevineindustries.scoretrackertdd.viewmodel

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FiveCrownsViewModelTests {
    @Test
    fun alert_dialog_state_updates() {
        val vm = createVm()
        assertFalse(vm.state.isExitGameDialogShowing)
        vm.updateExitGameDialogState(true)
        assertTrue(vm.state.isExitGameDialogShowing)
        vm.updateExitGameDialogState(false)
        assertFalse(vm.state.isExitGameDialogShowing)
    }

    @Test
    fun increment_wild_card() {
        val vm = createVm()
        assert(3 == vm.state.wildCard)
        vm.incrementWildCard()
        assert(4 == vm.state.wildCard)
    }

    @Test
    fun increment_dealer() {
        val vm = createVm()
        assert(0 == vm.state.dealer)
        vm.incrementDealer()
        assert(1 == vm.state.dealer)
    }

    @Test
    fun endgame() {
        val vm = createVm()
        assertFalse(vm.endgameCondition())
        while (vm.state.wildCard != 13) {
            vm.incrementWildCard()
        }
        assertTrue(vm.endgameCondition())
    }

//    @Test
//    fun reset_for_new_game() {
//        val expectedPlayersList = listOf(
//            Player("player0", 0, 0),
//            Player("player1", 0, 0),
//            Player("player2", 0, 0),
//        )
//        val expectedDealer = 6
//        val vm = createVm()
//
//        val numPlayers = FiveCrownsConstants.DEFAULT_NUM_PLAYERS
//        vm.createPlayersList(numPlayers)
//        vm.updateExitGameDialogState(true)
//        vm.updateCalcDialogState(true)
//        vm.incrementWildCard()
//        for (i in 1..expectedDealer) {
//            vm.incrementDealer()
//        }
//        addPlayerData(vm)
//        assert(FiveCrownsConstants.DEFAULT_NUM_PLAYERS == vm.playerList.size)
//        assert(vm.state == FiveCrownsState(
//            wildCard = 4,
//            dealer = expectedDealer,
//            isExitGameDialogShowing = true,
//            isCalcDialogShowing = true
//        ))
//        assert(vm.playerList[0].name == "player0")
//
//        vm.resetNewGame()
//
//        assert(numPlayers == vm.playerList.size)
//        assert(expectedPlayersList == vm.playerList)
//        assert(vm.state == FiveCrownsState(
//            wildCard = 3,
//            dealer = expectedDealer,
//            isExitGameDialogShowing = false,
//            isCalcDialogShowing = false
//        ))
//    }

    @Test
    fun players_list_created() {
        val vm = createVm()

//        vm.createPlayersList(numPlayers = 3)

        assert(3 == vm.playerList.size)
    }

    @Test
    fun update_name_in_players_list() {
        val vm = createVm()
//        vm.createPlayersList(3)
        val expectedName = "lombax"
        val playerIndex = 1

        vm.setName(playerIndex, expectedName)
        assert(expectedName == vm.playerList[playerIndex].name)
    }

    @Test
    fun update_score_in_players_list() {
        val vm = createVm()
//        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedScore = 45

        vm.setScore(playerIndex, expectedScore)
        assert(expectedScore == vm.playerList[playerIndex].score)
    }

    @Test
    fun update_points_in_players_list() {
        val vm = createVm()
//        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedPoints = 45

        vm.setPotentialPoints(playerIndex, expectedPoints)
        assert(expectedPoints == vm.playerList[playerIndex].pendingPoints)
    }

    @Test
    fun tally_scores() {
        val vm = createVm()
        val playerIndex = 1
        val expectedPoints = 45
        vm.setPotentialPoints(playerIndex, expectedPoints)
        val expectedPlayers = listOf(
            Player("player1", 0),
            Player("player2", expectedPoints),
            Player("player3", 0),
        )

        vm.tallyPoints()

        assert(expectedPlayers == vm.playerList)
    }

    fun addPlayerData(viewModel: FiveCrownsViewModel) {
        viewModel.playerList.forEachIndexed { index, _ ->
            viewModel.setName(index, "player$index")
            viewModel.setPotentialPoints(index, index + 1)
            viewModel.setScore(index, (index + 1) * 10)
        }
    }

    private fun createVm() = FiveCrownsViewModel()

}