package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreenTestTags
import com.grapevineindustries.scoretrackertdd.utils.AddPlayersTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FinalScoresTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsCalcDialogTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.utils.LandingScreenTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScoreTrackerNavigationTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val landingScreenUtils = LandingScreenTestUtils(composeTestRule)
    private val addPlayersUtils = AddPlayersTestUtils(composeTestRule)
    private val fiveCrownsScreenUtils = FiveCrownsScreenTestUtils(composeTestRule)
    private val finalScoresUtils = FinalScoresTestUtils(composeTestRule)
    private val fiveCrownsCalcDialogUtils = FiveCrownsCalcDialogTestUtils(composeTestRule)

    @Test
    fun to_addPlayer_screen() {
        launchNavController()

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()

        addPlayersUtils.assertScreenShowing()
    }

    @Test
    fun to_fiveCrowns_screen() {
        val gameViewModel = GameViewModel()
        gameViewModel.createPlayersList(3)

        launchNavController(gameViewModel = gameViewModel)
        landingScreenUtils.clickAddPlayers()

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
        val gameViewModel = GameViewModel()
        gameViewModel.createPlayersList(3)
        gameViewModel.updatePlayer(0, Player(name = "player1"))
        gameViewModel.updatePlayer(1, Player(name = "player2"))
        gameViewModel.updatePlayer(2, Player(name = "player3"))

        launchNavController(gameViewModel = gameViewModel)
        landingScreenUtils.clickAddPlayers()
        addPlayersUtils.clickStartGame()

        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.clickEndGameTally()

        finalScoresUtils.assertShowing()
    }

    @Test
    fun finalScores_click_new_game() {
        val gameViewModel = GameViewModel()
        launchNavController(gameViewModel = gameViewModel)

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()
        addPlayersUtils.clickStartGame()
        fiveCrownsScreenUtils.clickEndGameTally()
        finalScoresUtils.clickNewGame()

        landingScreenUtils.assertInitialContentDisplayed()
        assertEquals(3, gameViewModel.state.value.wildCard)
    }

    @Test
    fun finalScores_click_replay() {
        launchNavController()

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()
        addPlayersUtils.clickStartGame()
        fiveCrownsScreenUtils.clickEndGameTally()
        finalScoresUtils.clickReplay()

        fiveCrownsScreenUtils.assertScreenShowing()
    }

    @Test
    fun five_crowns_flow() {
        launchNavController()

        landingScreenUtils.assertInitialContentDisplayed()
        landingScreenUtils.clickAddPlayers()

        addPlayersUtils.clickStartGame()

        fiveCrownsScreenUtils.clickFirstCalculatorButton()
        fiveCrownsCalcDialogUtils.clickButton("10")
        fiveCrownsCalcDialogUtils.clickConfirmButton()
        composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.TALLY_BUTTON)
            .assertIsDisplayed()
            .performClick()

        fiveCrownsScreenUtils.clickEndGameTally(startWildCard = 4)

        finalScoresUtils.assertShowing()
        finalScoresUtils.assertData(
            listOf(
                Player(name = "", score = 0),
                Player(name = "", score = 0),
                Player(name = "", score = 10),
            )
        )
        finalScoresUtils.clickNewGame()

        landingScreenUtils.assertInitialContentDisplayed()
    }

    private fun launchNavController(
        gameViewModel: GameViewModel = GameViewModel()
    ) {
        composeTestRule.setContent {
            MainAppNavHost(
                mainNavController = rememberNavController(),
                gameViewModel = gameViewModel
            )
        }
    }
}