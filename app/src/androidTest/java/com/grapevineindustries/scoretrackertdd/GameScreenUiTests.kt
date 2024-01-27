package com.grapevineindustries.scoretrackertdd

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.GameScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayersViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        GameScreenTestUtils.setup(composeTestRule)

        val playersViewModel = PlayersViewModel()
        playersViewModel.createPlayersList(3)

        val gameViewModel = GameViewModel()
        composeTestRule.setContent {
            ScoreTrackerTheme {
                GameScreen(
                    onBackPressed = {  },
                    players = playersViewModel.playerList,
                    backDialogState = gameViewModel.backDialogState.collectAsState().value,
                    updateDialogState = gameViewModel::updateDialogState
                )
            }
        }
    }

    @Test
    fun back_press_chicken_test() {
        GameScreenTestUtils.assertScreenShowing()

        GameScreenTestUtils.clickBack()
        AlertDialogTestUtils.assertShowing(

        )
    }
}