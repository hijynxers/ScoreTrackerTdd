package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.grapevineindustries.scoretrackertdd.ui.GameScreenTestTags

object GameScreenTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    fun assertScreenShowing(
        numPlayers: Int = FiveCrownsConstants.DEFAULT_NUM_PLAYERS
    ) {
        composeTestRule.onNodeWithTag(GameScreenTestTags.GAME_SCREEN)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(GameScreenTestTags.WILD_CARD)
            .assertIsDisplayed()
            .assertTextEquals(FiveCrownsConstants.INITIAL_WILD_CARD.toString())

        composeTestRule.onNodeWithTag(GameScreenTestTags.PLAYER_COLUMN)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(numPlayers)

        composeTestRule.onNodeWithTag(GameScreenTestTags.TALLY_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertTextEquals("Tally")
    }

    fun clickBack() {
//        composeTestRule.activityRule.scenario.onActivity { activity ->
//            activity.onBackPressedDispatcher.onBackPressed()
//        }

    }
}