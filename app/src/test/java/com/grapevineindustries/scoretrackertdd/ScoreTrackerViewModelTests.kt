package com.grapevineindustries.scoretrackertdd

import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import org.junit.Test

class ScoreTrackerViewModelTests {
    @Test
    fun players_list_created() {
        val vm = ScoreTrackerViewModel()

        vm.createPlayersList(numPlayers = 3)

        assert(3 == vm.playerList.size)
    }

    @Test
    fun update_name_in_players_list() {
        val vm = ScoreTrackerViewModel()
        vm.createPlayersList(3)
        val expectedName = "nathan"
        val playerIndex = 1

        vm.setName(playerIndex, expectedName)
        assert(expectedName == vm.playerList[playerIndex].name)
    }

    @Test
    fun update_score_in_players_list() {
        val vm = ScoreTrackerViewModel()
        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedScore = 45

        vm.setScore(playerIndex, expectedScore)
        assert(expectedScore == vm.playerList[playerIndex].score)
    }

    @Test
    fun update_points_in_players_list() {
        val vm = ScoreTrackerViewModel()
        vm.createPlayersList(3)
        val playerIndex = 1
        val expectedPoints = 45

        vm.setPotentialPoints(playerIndex, expectedPoints)
        assert(expectedPoints == vm.playerList[playerIndex].pendingPoints)
    }

    @Test
    fun tally_scores() {
        val vm = ScoreTrackerViewModel()
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
    fun increment_dealer() {
        val vm = ScoreTrackerViewModel()
        assert(0 == vm.dealer.value)
        vm.incrementDealer()
        assert(1 == vm.dealer.value)
    }

    @Test
    fun reset_view_model() {
        val vm = ScoreTrackerViewModel()
        vm.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
        assert(FiveCrownsConstants.DEFAULT_NUM_PLAYERS == vm.playerList.size)
        vm.incrementDealer()
        assert(1 == vm.dealer.value)

        vm.reset()
        assert(0 == vm.playerList.size)
        assert(0 == vm.dealer.value)
    }

    @Test
    fun reset_scores() {
        val vm = ScoreTrackerViewModel()
        vm.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
        vm.setScore(0, 22)
        vm.setScore(1, 3)
        vm.setScore(2, 12)
        val expectedPlayers = listOf(
            Player("", 0),
            Player("", 0),
            Player("", 0),
        )
        vm.resetScores()

        assert(expectedPlayers == vm.playerList)
    }

    @Test
    fun sort_by_score() {
        val vm = ScoreTrackerViewModel()
        vm.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
        vm.setScore(0, 22)
        vm.setScore(1, 3)
        vm.setScore(2, 12)
        val expectedPlayers = listOf(
            Player("", 3),
            Player("", 12),
            Player("", 22),
        )

        assert(expectedPlayers == vm.sortedPlayers())
    }
}