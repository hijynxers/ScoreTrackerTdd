package com.grapevineindustries.fivecrowns.ui

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.fivecrowns.testutils.LandingScreenTestUtils
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LandingScreenUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val landingScreenUtils = LandingScreenTestUtils(composeTestRule)

    private fun launchWithCompose(
        onAddPlayersClick: (Int) -> Unit = {},
        onNavigateToHighLowScores: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            LandingScreen(
                onAddPlayersClick = onAddPlayersClick,
                onNavigateToHighLowScores = onNavigateToHighLowScores
            )
        }
    }

    @Test
    fun whenHighLowScoresClicked_thenNavigates() {
        var navigated = false
        launchWithCompose(onNavigateToHighLowScores = { navigated = true })

        composeTestRule.onNodeWithTag(LandingScreenTestTags.HIGH_LOW_SCORE_BUTTON)
            .performClick()

        assertTrue(navigated)
    }

    @Test
    fun add_and_remove_number_of_players() {
        launchWithCompose()
        landingScreenUtils.assertInitialContentDisplayed()

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_ADD)
            .performClick()
        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("4")

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_MINUS)
            .performClick()
        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("3")
    }

    @Test
    fun min_players_reached() {
        launchWithCompose()
        landingScreenUtils.assertInitialContentDisplayed()

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_MINUS)
            .performClick()
        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("2")

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_MINUS)
            .performClick()
        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("2")
    }

    @Test
    fun max_players_reached() {
        launchWithCompose()
        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.reachMaxPlayerFromInitial()
        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("15")

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_ADD)
            .performClick()

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("15")
    }

    @Test
    fun player_list_created_on_add_players_click() {
        var playersAdded = false
        launchWithCompose(onAddPlayersClick = { playersAdded = true })
        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()
        assertTrue(playersAdded)
    }
}