package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.navigation.NavHost
import com.grapevineindustries.scoretrackertdd.navigation.NavHostRoutes
import com.grapevineindustries.scoretrackertdd.utils.AddPlayersTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FinalScoresTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.utils.LandingScreenTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayerViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScoreTrackerNavigationTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val playerViewModel = PlayerViewModel()

    private val landingScreenUtils = LandingScreenTestUtils(composeTestRule)
    private val addPlayersUtils = AddPlayersTestUtils(composeTestRule)
    private val fiveCrownsScreenUtils = FiveCrownsScreenTestUtils(composeTestRule)
    private val finalScoresUtils = FinalScoresTestUtils(composeTestRule)

    @Test
    fun to_addPlayer_screen() {
        composeTestRule.setContent {
            NavHost()
        }

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()

        addPlayersUtils.assertScreenShowing()
    }

    @Test
    fun to_fiveCrowns_screen() {
        composeTestRule.setContent {
            NavHost(
                startDestination = NavHostRoutes.AddPlayersScreen
            )
        }

        addPlayersUtils.assertScreenShowing()
        addPlayersUtils.addAndAssertPlayerNameText(0, "player1")
        addPlayersUtils.addAndAssertPlayerNameText(1, "player2")
        addPlayersUtils.addAndAssertPlayerNameText(2, "player3")
        addPlayersUtils.clickStartGame()

        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.assertPlayerData(
            listOf(
                Player("player1"),
                Player("player2"),
                Player("player3")
            )
        )
    }

    @Test
    fun to_finalScores_screen() {
        composeTestRule.setContent {
            NavHost(
                startDestination = NavHostRoutes.GameScreen,
                playerViewModel = playerViewModel
            )
        }

        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.clickEndGameTally()

        finalScoresUtils.assertShowing()
    }

    @Test
    fun finalScores_click_new_game() {
        composeTestRule.setContent {
            NavHost()
        }

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()
        addPlayersUtils.clickStartGame()
        fiveCrownsScreenUtils.clickEndGameTally()
        finalScoresUtils.clickNewGame()

        landingScreenUtils.assertInitialContentDisplayed()
    }

    @Test
    fun finalScores_click_replay() {
        composeTestRule.setContent {
            NavHost()
        }

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()
        addPlayersUtils.clickStartGame()
        fiveCrownsScreenUtils.clickEndGameTally()
        finalScoresUtils.clickReplay()

        fiveCrownsScreenUtils.assertScreenShowing()
    }
}