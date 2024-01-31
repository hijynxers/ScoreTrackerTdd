package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import com.grapevineindustries.scoretrackertdd.ui.FinalScoresScreenTestTags
import com.grapevineindustries.scoretrackertdd.viewmodel.Player

object FinalScoresTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    fun assertShowing(playerData: List<Player>) {
        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.FINAL_SCORES_SCREEN)
            .assertIsDisplayed()

        val playerNameNodes = composeTestRule.onAllNodesWithTag(FinalScoresScreenTestTags.PLAYER_NAME)
        val playerScoreNodes = composeTestRule.onAllNodesWithTag(FinalScoresScreenTestTags.PLAYER_SCORE)

        playerData.forEachIndexed { index, player ->
            playerNameNodes[index].assertTextEquals(player.name)
            playerScoreNodes[index].assertTextEquals(player.score.toString())
        }

        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.REPLAY)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(FinalScoresScreenTestTags.NEW_GAME)
            .assertIsDisplayed()
    }
}