package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        MainScreenTestUtils.setup(composeTestRule)
    }

    @Test
    fun add_and_remove_number_of_players() {
        composeTestRule.setContent {
            MainScreen()
        }

        MainScreenTestUtils.assertInitialContentDisplayed()

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_ADD)
            .performClick()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("4")

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_MINUS)
            .performClick()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("3")
    }

    @Test
    fun min_players_reached() {
        composeTestRule.setContent {
            MainScreen()
        }

        MainScreenTestUtils.assertInitialContentDisplayed()

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_MINUS)
            .performClick()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("2")

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_MINUS)
            .performClick()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("2")
    }

    @Test
    fun max_players_reached() {
        composeTestRule.setContent {
            MainScreen()
        }

        MainScreenTestUtils.assertInitialContentDisplayed()
        MainScreenTestUtils.reachMaxPlayerFromInitial()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("15")

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS_ADD)
            .performClick()

        composeTestRule.onNodeWithTag(MainScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("15")
    }
}