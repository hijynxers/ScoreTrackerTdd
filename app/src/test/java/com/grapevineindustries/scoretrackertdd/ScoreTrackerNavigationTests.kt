package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.navigation.NavHost
import com.grapevineindustries.scoretrackertdd.navigation.NavHostRoutesEnum
import com.grapevineindustries.scoretrackertdd.utils.AddPlayersTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FinalScoresTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.utils.LandingScreenTestUtils
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
                startDestination = NavHostRoutesEnum.AddPlayersScreen.name
            )
        }


        addPlayersUtils.assertScreenShowing(0)
        addPlayersUtils.clickStartGame()

        fiveCrownsScreenUtils.assertScreenShowing(0)
    }

    @Test
    fun to_finalScores_screen() {
        composeTestRule.setContent {
            NavHost(
                startDestination = NavHostRoutesEnum.GameScreen.name
            )
        }

        fiveCrownsScreenUtils.assertScreenShowing(0)
        fiveCrownsScreenUtils.clickEndGameTally()

        finalScoresUtils.assertShowing()
    }
}