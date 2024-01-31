package com.grapevineindustries.scoretrackertdd

import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayersViewModel
import org.junit.Test

class PlayerViewModelTests {
    @Test
    fun players_list_created() {
        val vm = PlayersViewModel()

        vm.createPlayersList(numPlayers = 3)

        assert(3 == vm.playerList.size)
    }

    @Test
    fun update_name_in_players_list() {
        val vm = PlayersViewModel()
        vm.createPlayersList(3)
        val expectedName = "nathan"
        val playerIndex = 1

        vm.setName(playerIndex, expectedName)
        assert(expectedName == vm.playerList[playerIndex].name)
    }

    @Test
    fun update_score_in_players_list() {
        val vm = PlayersViewModel()
        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedScore = 45

        vm.setScore(playerIndex, expectedScore)
        assert(expectedScore == vm.playerList[playerIndex].score)
    }

    @Test
    fun update_points_in_players_list() {
        val vm = PlayersViewModel()
        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedPoints = 45

        vm.setPotentialPoints(playerIndex, expectedPoints)
        assert(expectedPoints == vm.playerList[playerIndex].pendingPoints)
    }

    @Test
    fun tally_scores() {
        val vm = PlayersViewModel()
        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedPoints = 45
        vm.setPotentialPoints(playerIndex, expectedPoints)
        val expectedPlayers = listOf(
            Player("", 0),
            Player("", expectedPoints),
            Player("", 0),
        )

        vm.tallyPoints()

        assert(expectedPlayers == vm.playerList)
    }

    @Test
    fun reset_view_model() {
        val vm = PlayersViewModel()
        vm.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
        assert(FiveCrownsConstants.DEFAULT_NUM_PLAYERS == vm.playerList.size)

        vm.reset()
        assert(0 == vm.playerList.size)
    }
}