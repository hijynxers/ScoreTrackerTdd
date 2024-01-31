package com.grapevineindustries.scoretrackertdd

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.grapevineindustries.scoretrackertdd.GameScreenTestUtils.assertWildCard
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.GameScreen
import com.grapevineindustries.scoretrackertdd.ui.GameScreenTestTags
import com.grapevineindustries.scoretrackertdd.ui.composables.convertWildCard
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

    private val initialPlayerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        GameScreenTestUtils.setup(composeTestRule)
        AlertDialogTestUtils.setup(composeTestRule)
        CalcDialogTestUtils.setup(composeTestRule)

        val playersViewModel = PlayersViewModel()

        GameScreenTestUtils.initPlayerList(
            viewModel = playersViewModel,
            playerNames = initialPlayerData
        )

        val gameViewModel = GameViewModel()
        backNavigation = false

        composeTestRule.setContent {
            ScoreTrackerTheme {
                GameScreen(
                    onCloseGame = { backNavigation = true },
                    players = playersViewModel.playerList,
                    exitGameDialogState = gameViewModel.exitGameDialogState.collectAsState().value,
                    updateExitGameDialogState = gameViewModel::updateExitGameDialogState,
                    updatePotentialPoints = playersViewModel::setPotentialPoints,
                    tallyPoints = {gameViewModel.incrementWildCard()},
                    wildCard = gameViewModel.wildCard.collectAsState().value
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

        GameScreenTestUtils.assertPlayerData(initialPlayerData)
    }

    @Test
    fun add_score() {
        GameScreenTestUtils.assertScreenShowing()
        GameScreenTestUtils.clickFirstCalculatorButton()

        GameScreenTestUtils.assertCalculatorShowing()

        CalcDialogTestUtils.clickButton("K")

        CalcDialogTestUtils.clickConfirmButton()
        GameScreenTestUtils.assertCalculatorNotShowing()
        var expectedPlayerData = listOf(
            Player("player1", 3, 13),
            Player("player2", 15),
            Player("player3", 183),
        )
        GameScreenTestUtils.assertPlayerData(expectedPlayerData)

        GameScreenTestUtils.clickTallyButton()
        composeTestRule.onNodeWithTag(GameScreenTestTags.WILD_CARD)
            .assertTextEquals("4")
        expectedPlayerData = listOf(
            Player("player1", 26),
            Player("player2", 15),
            Player("player3", 183),
        )
        GameScreenTestUtils.assertPlayerData(expectedPlayerData)
    }

    @Test
    fun calc_dialog_cancel_does_not_add_points() {
        GameScreenTestUtils.assertScreenShowing()
        GameScreenTestUtils.clickFirstCalculatorButton()
        GameScreenTestUtils.assertCalculatorShowing()
        CalcDialogTestUtils.clickButton("K")

        CalcDialogTestUtils.clickCancelButton()

        GameScreenTestUtils.assertCalculatorNotShowing()
        GameScreenTestUtils.assertPlayerData(initialPlayerData)
    }

    @Test
    fun wildcard_displays_correctly() {
        GameScreenTestUtils.assertScreenShowing()

        for (i in 4..13) {
            GameScreenTestUtils.clickTallyButton()
            assertWildCard("WILD CARD: ${convertWildCard(i)}")
        }
    }
}