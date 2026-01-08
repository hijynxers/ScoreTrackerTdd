package com.grapevineindustries.fivecrowns.utils

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.fivecrowns.Player
import com.grapevineindustries.fivecrowns.ui.FinalScoresScreenTestTags

class FinalScoresTestUtils(
    val composeTestRule: ComposeTestRule
) {
    fun assertShowing() {
        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.REPLAY)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.NEW_GAME)
            .assertIsDisplayed()
    }

    fun assertData(playerData: List<Player>) {
        val playerNameNodes = composeTestRule.onAllNodesWithTag(FinalScoresScreenTestTags.PLAYER_NAME)
        val playerScoreNodes = composeTestRule.onAllNodesWithTag(FinalScoresScreenTestTags.PLAYER_SCORE)

        playerData.forEachIndexed { index, player ->
            playerNameNodes[index].assertTextEquals(player.name)
            playerScoreNodes[index].assertTextEquals(player.score.toString())
        }
    }

    fun clickNewGame() {
        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.NEW_GAME).performClick()
    }

    fun clickReplay() {
        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.REPLAY).performClick()
    }
}