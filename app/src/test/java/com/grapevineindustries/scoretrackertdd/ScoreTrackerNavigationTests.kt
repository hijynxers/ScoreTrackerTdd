package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.navigation.NavHost
import com.grapevineindustries.scoretrackertdd.navigation.NavHostRoutesEnum
import com.grapevineindustries.scoretrackertdd.utils.AddPlayersTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FinalScoresTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.utils.LandingScreenTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import org.junit.Before
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

    private val scoreTrackerViewModel = ScoreTrackerViewModel()

    @Before
    fun setup() {
        scoreTrackerViewModel.createPlayersList(3)
    }

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
                startDestination = NavHostRoutesEnum.AddPlayersScreen.name,
                scoreTrackerViewModel = scoreTrackerViewModel
            )
        }


        addPlayersUtils.assertScreenShowing()
        addPlayersUtils.clickStartGame()

        fiveCrownsScreenUtils.assertScreenShowing()
    }

    @Test
    fun to_finalScores_screen() {
        composeTestRule.setContent {
            NavHost(
                startDestination = NavHostRoutesEnum.GameScreen.name,
                scoreTrackerViewModel = scoreTrackerViewModel
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