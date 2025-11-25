package com.grapevineindustries.fivecrowns.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.fivecrowns.Player
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.utils.FinalScoresTestUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FinalScoresUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val finalScoresUtils = FinalScoresTestUtils(composeTestRule)

    private val playerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FinalScoreScreen(
                    playerData = playerData,
                    onReplayClick = {},
                    onNewGameClick = {}
                )
            }
        }
    }

    @Test
    fun screen_showing() {
        finalScoresUtils.assertShowing()
        finalScoresUtils.assertData(playerData)
    }
}