package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

object MainScreenTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }


    fun assertInitialContentDisplayed() {
        composeTestRule.onNodeWithTag(MainScreenTestTags.TITLE)
            .assertIsDisplayed()
            .assertTextEquals("Five Crowns")

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertIsDisplayed()
            .assertTextEquals("3")

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_MINUS)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_ADD)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule.onNodeWithTag(MainScreenTestTags.ADD_PLAYERS_BUTTON)
            .assertIsDisplayed()
    }

    fun reachMaxPlayerFromInitial() {
        for (i in 3..14) {
            composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_ADD)
                .performClick()
        }
    }
}

