package com.grapevineindustries.scoretrackertdd.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.utils.LandingScreenTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LandingScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val viewModel = ScoreTrackerViewModel()
    private val landingScreenUtils = LandingScreenTestUtils(composeTestRule)
    private var gameOption: GameOption = GameOption.FIVE_CROWNS
    @Before
    fun setup() {
        composeTestRule.setContent {
            LandingScreen(
                updateGame = { viewModel.updateGame(gameOption) },
                game = viewModel.game.collectAsState().value,
                onAddPlayersClick = {
                    viewModel.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)
                }
            )
        }
    }

    @Test
    fun add_and_remove_number_of_players() {
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
        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()

        assert(FiveCrownsConstants.DEFAULT_NUM_PLAYERS == viewModel.playerList.size)
    }

    @Test
    fun display_navigation_bar() {
        landingScreenUtils.assertInitialContentDisplayed()

        landingScreenUtils.clickNavigationBar()
        landingScreenUtils.assertNavigationBarButtons()
        gameOption = GameOption.RUMMY
        landingScreenUtils.clickRummy()
        landingScreenUtils.assertRummyShowing()

        landingScreenUtils.clickNavigationBar()
        landingScreenUtils.assertNavigationBarButtons()
        gameOption = GameOption.FIVE_CROWNS
        landingScreenUtils.clickFiveCrowns()
        landingScreenUtils.assertInitialContentDisplayed()
    }
}