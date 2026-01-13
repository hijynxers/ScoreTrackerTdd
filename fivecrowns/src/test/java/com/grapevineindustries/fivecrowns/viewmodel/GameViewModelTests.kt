package com.grapevineindustries.fivecrowns.viewmodel

import com.grapevineindustries.fivecrowns.data.FiveCrownsConstants
import com.grapevineindustries.fivecrowns.data.Player
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GameViewModelTests {

    @Test
    fun updateExitGameDialogState_updatesState() {
        val vm = createVm()
        assertFalse(vm.state.value.isExitGameDialogShowing)
        vm.updateExitGameDialogState(true)
        assertTrue(vm.state.value.isExitGameDialogShowing)
        vm.updateExitGameDialogState(false)
        assertFalse(vm.state.value.isExitGameDialogShowing)
    }

    @Test
    fun updateCalcDialogState_updatesState() {
        val vm = createVm()
        assertFalse(vm.state.value.isCalcDialogShowing)
        vm.updateCalcDialogState(true)
        assertTrue(vm.state.value.isCalcDialogShowing)
        vm.updateCalcDialogState(false)
        assertFalse(vm.state.value.isCalcDialogShowing)
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

    @Test
    fun createPlayersList_createsCorrectNumberOfPlayers() {
        val vm = GameViewModel()
        val numPlayers = 5
        vm.createPlayersList(numPlayers)

        assertEquals(numPlayers, vm.players.size)
        vm.players.forEach { player ->
            assertEquals("", player.name)
            assertEquals(0, player.score)
            assertEquals(0, player.pendingPoints)
        }
    }

    @Test
    fun createPlayersList_resetsExistingPlayers() {
        val vm = GameViewModel()
        // Set up initial state with some players
        vm.createPlayersList(3)
        vm.updatePlayer(0, Player(name = "Player 1", score = 50))

        // Call the function to test
        val newNumPlayers = 2
        vm.createPlayersList(newNumPlayers)

        // Assert that the player list was cleared and recreated
        assertEquals(newNumPlayers, vm.players.size)
        assertEquals(Player(name = ""), vm.players[0])
        assertEquals(Player(name = ""), vm.players[1])
    }

    @Test
    fun createPlayersList_withZeroPlayers_createsEmptyList() {
        val vm = GameViewModel()
        vm.createPlayersList(0)
        assertTrue(vm.players.isEmpty())
    }

    @Test
    fun updatePlayer() {
        val vm = createVm()
        val expectedPlayer = Player(name = "nathan")
        val index = 0
        assert(vm.players[index] == Player(name = ""))
        vm.updatePlayer(index, expectedPlayer)

        assert(vm.players[index] == expectedPlayer)
    }

    @Test
    fun reset_view_model() {
        val vm = createVm()
        vm.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
        assert(FiveCrownsConstants.DEFAULT_NUM_PLAYERS == vm.players.size)

        vm.reset()
        assert(0 == vm.players.size)
    }

    @Test
    fun setScore_updatesScoreInPlayersList() {
        val vm = createVm()
        val playerIndex = 1
        val expectedScore = 45

        vm.setScore(playerIndex, expectedScore)
        assert(expectedScore == vm.players[playerIndex].score)
    }

    @Test
    fun update_points_in_players_list() {
        val vm = createVm()
        val playerIndex = 1
        val expectedPoints = 45

        vm.setPotentialPoints(playerIndex, expectedPoints)
        assert(expectedPoints == vm.players[playerIndex].pendingPoints)
    }

    @Test
    fun tally_scores() {
        val vm = createVm()
        val playerIndex = 1
        val expectedPoints = 45
        vm.setPotentialPoints(playerIndex, expectedPoints)
        val expectedPlayers = listOf(
            Player("", 0),
            Player("", expectedPoints),
            Player("", 0),
        )

        vm.tallyPoints()

        assert(expectedPlayers == vm.players)
        assertFalse(vm.state.value.isNoScoreDialogShowing)
    }

    @Test
    fun tally_scores_with_no_score_shows_dialog() {
        val vm = createVm()
        val expectedPlayers = listOf(
            Player("", 0, pendingPoints = 0),
            Player("", 0, pendingPoints = 0),
            Player("", 0, pendingPoints = 0),
        )

        vm.tallyPoints()

        assert(expectedPlayers == vm.players)
        assertTrue(vm.state.value.isNoScoreDialogShowing)
    }

    @Test
    fun resetScores_resetsAllPlayerScoresAndPendingPoints() {
        val vm = createVm()
        vm.updatePlayer(0, Player(name = "Player 1", score = 10, pendingPoints = 5))
        vm.updatePlayer(1, Player(name = "Player 2", score = 20, pendingPoints = 10))

        vm.resetScores()

        vm.players.forEach { player ->
            assertEquals(0, player.score)
            assertEquals(0, player.pendingPoints)
        }
    }

    @Test
    fun sortedPlayers_returnsPlayersSortedByScore() {
        val vm = createVm()
        vm.updatePlayer(0, Player(name = "Player 1", score = 50))
        vm.updatePlayer(1, Player(name = "Player 2", score = 20))
        vm.updatePlayer(2, Player(name = "Player 3", score = 30))

        val sorted = vm.sortedPlayers()

        assertEquals("Player 2", sorted[0].name)
        assertEquals("Player 3", sorted[1].name)
        assertEquals("Player 1", sorted[2].name)
    }

    @Test
    fun resetWildCard_resetsWildCardToDefault() {
        val vm = createVm()
        vm.incrementWildCard()
        vm.incrementWildCard()
        assertEquals(5, vm.state.value.wildCard)

        vm.resetWildCard()

        assertEquals(3, vm.state.value.wildCard)
    }

    @Test
    fun resetGameAndPlayers_clearsPlayersAndResetsState() {
        val vm = createVm()
        vm.updatePlayer(0, Player(name = "Player 1", score = 50))
        vm.incrementWildCard()

        vm.resetGameAndPlayers()

        assertTrue(vm.players.isEmpty())
        assertEquals(3, vm.state.value.wildCard)
    }

    private fun createVm(
        numPlayers: Int = 3
    ): GameViewModel {
        val vm = GameViewModel()
        vm.createPlayersList(numPlayers)
        return vm
    }
}