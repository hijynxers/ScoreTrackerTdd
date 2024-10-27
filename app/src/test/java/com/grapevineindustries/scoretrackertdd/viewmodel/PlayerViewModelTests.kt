package com.grapevineindustries.scoretrackertdd.viewmodel

import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import org.junit.Test

class PlayerViewModelTests {
    @Test
    fun players_list_created() {
        val vm = createVm()

        assert(3 == vm.players.size)
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
    fun update_score_in_players_list() {
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
    }

    private fun createVm(
        numPlayers: Int = 3
    ): PlayerViewModel {
        val vm = PlayerViewModel()
        vm.createPlayersList(numPlayers)
        return vm
    }
}