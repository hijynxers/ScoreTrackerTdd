package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

object LandingScreenTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    fun assertInitialContentDisplayed() {
        composeTestRule.onNodeWithTag(LandingScreenTestTags.GAME_TITLE)
            .assertIsDisplayed()
            .assertTextEquals("Five Crowns")

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertIsDisplayed()
            .assertTextEquals("3")

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_MINUS)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_ADD)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag(LandingScreenTestTags.ADD_PLAYERS_BUTTON)
            .assertIsDisplayed()
    }

    fun reachMaxPlayerFromInitial() {
        for (i in 3..14) {
            composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_ADD)
                .performClick()
        }
    }

    fun clickAddPlayers() {
        composeTestRule.onNodeWithTag(LandingScreenTestTags.ADD_PLAYERS_BUTTON)
            .performClick()
    }
}

