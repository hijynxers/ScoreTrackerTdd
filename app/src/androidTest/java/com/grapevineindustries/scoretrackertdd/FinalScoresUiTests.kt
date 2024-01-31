package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FinalScoresUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val playerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        FinalScoresTestUtils.setup(composeTestRule)

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
        FinalScoresTestUtils.assertShowing(playerData)
    }
}