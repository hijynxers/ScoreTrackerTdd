package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.scoretrackertdd.ui.GameOption
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreenTestTags
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LandingScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val viewModel = ScoreTrackerViewModel()

    @Before
    fun setup() {
        LandingScreenTestUtils.setup(composeTestRule)
        composeTestRule.setContent {
            LandingScreen(
                updateGame = {},
                game = GameOption.FIVE_CROWNS,
                onAddPlayersClick = {
                    viewModel.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
                }
            )
        }
    }

    @Test
    fun add_and_remove_number_of_players() {
        LandingScreenTestUtils.assertInitialContentDisplayed()

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
        LandingScreenTestUtils.assertInitialContentDisplayed()

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
        LandingScreenTestUtils.assertInitialContentDisplayed()
        LandingScreenTestUtils.reachMaxPlayerFromInitial()
        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("15")

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS_ADD)
            .performClick()

        composeTestRule.onNodeWithTag(LandingScreenTestTags.NUM_PLAYERS)
            .assertTextEquals("15")
    }

    @Test
    fun player_list_created_on_add_players_click() {
        LandingScreenTestUtils.assertInitialContentDisplayed()
        LandingScreenTestUtils.clickAddPlayers()

        assert(FiveCrownsConstants.DEFAULT_NUM_PLAYERS == viewModel.playerList.size)
    }

    @Test
    fun display_navigation_bar() {
        LandingScreenTestUtils.assertInitialContentDisplayed()
        LandingScreenTestUtils.clickNavigationBar()
        LandingScreenTestUtils.assertNavigationBarButtons()
        LandingScreenTestUtils.clickFiveCrowns()
        LandingScreenTestUtils.assertInitialContentDisplayed()
        LandingScreenTestUtils.clickNavigationBar()
        LandingScreenTestUtils.clickRummy()
        LandingScreenTestUtils.assertRummyShowing()
    }
}