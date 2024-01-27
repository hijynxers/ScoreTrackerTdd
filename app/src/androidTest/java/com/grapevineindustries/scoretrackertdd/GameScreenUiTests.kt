package com.grapevineindustries.scoretrackertdd

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.GameScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayersViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()
    private var backNavigation = false

    private val playerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        GameScreenTestUtils.setup(composeTestRule)
        AlertDialogTestUtils.setup(composeTestRule)

        val playersViewModel = PlayersViewModel()

        GameScreenTestUtils.initPlayerList(
            viewModel = playersViewModel,
            playerNames = playerData
        )

        val gameViewModel = GameViewModel()
        backNavigation = false

        composeTestRule.setContent {
            ScoreTrackerTheme {
                GameScreen(
                    onCloseGame = { backNavigation = true },
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
        assertFalse(backNavigation)

        // verify confirm button doesn't navigate
        GameScreenTestUtils.clickBack()
        AlertDialogTestUtils.assertShowing(
            title = "Are you sure you want to quit?",
            text = "If you leave you will lose game progress.",
            confirmButtonText = "Stay",
            dismissButtonText = "Exit",
        )
        AlertDialogTestUtils.clickConfirmButton()
        AlertDialogTestUtils.assertNotShowing()
        assertFalse(backNavigation)

        // verify dismiss button does navigate
        GameScreenTestUtils.assertScreenShowing()
        GameScreenTestUtils.clickBack()
        AlertDialogTestUtils.assertShowing(
            title = "Are you sure you want to quit?",
            text = "If you leave you will lose game progress.",
            confirmButtonText = "Stay",
            dismissButtonText = "Exit",
        )
        AlertDialogTestUtils.clickDismissButton()
        AlertDialogTestUtils.assertNotShowing()
        assertTrue(backNavigation)
    }

    @Test
    fun player_list_shows_correct_data() {
        GameScreenTestUtils.assertScreenShowing()

        GameScreenTestUtils.assertPlayerData(playerData)
    }
}