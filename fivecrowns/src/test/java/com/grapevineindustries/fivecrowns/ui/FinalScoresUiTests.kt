package com.grapevineindustries.fivecrowns.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.Player
import com.grapevineindustries.fivecrowns.testutils.FinalScoresTestUtils
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FinalScoresUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val finalScoresUtils = FinalScoresTestUtils(composeTestRule)

    private val playerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Test
    fun screen_showing() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FinalScoreScreen(
                    playerData = playerData,
                    onReplayClick = {},
                    onNewGameClick = {}
                )
            }
        }

        finalScoresUtils.assertShowing()
        finalScoresUtils.assertData(playerData)
    }
}