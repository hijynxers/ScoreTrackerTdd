package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                NavHost()
            }
        }
        LandingScreenTestUtils.setup(composeTestRule)
        AddPlayersTestUtils.setup(composeTestRule)
    }

    @Test
    fun navigates_to_main_screen() {
        LandingScreenTestUtils.assertInitialContentDisplayed()
    }

    @Test
    fun navigates_to_add_players_screen() {
        LandingScreenTestUtils.clickAddPlayers()
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.TestTag)
            .assertIsDisplayed()
    }

}