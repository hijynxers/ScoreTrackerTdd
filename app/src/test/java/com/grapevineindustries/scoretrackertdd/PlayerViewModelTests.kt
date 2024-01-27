package com.grapevineindustries.scoretrackertdd

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
}