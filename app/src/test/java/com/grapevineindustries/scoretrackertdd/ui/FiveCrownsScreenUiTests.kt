package com.grapevineindustries.scoretrackertdd.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.utils.AlertDialogTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsCalcDialogTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsViewModel
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FiveCrownsScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fiveCrownsScreenUtils = FiveCrownsScreenTestUtils(composeTestRule)
    private val alertDialogUtils = AlertDialogTestUtils(composeTestRule)
    private val fiveCrownsCalcDialogUtils = FiveCrownsCalcDialogTestUtils(composeTestRule)

    private val scoreTrackerViewModel = ScoreTrackerViewModel()
    private val fiveCrownsViewModel = FiveCrownsViewModel()
    private var backNavigation = false
    private var endGameNavigation = false

    private val initialPlayerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        fiveCrownsScreenUtils.initPlayerList(
            viewModel = scoreTrackerViewModel,
            playerNames = initialPlayerData
        )
        backNavigation = false

        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreen(
                    onCloseGame = { backNavigation = true },
                    tallyPoints = {
                        scoreTrackerViewModel.tallyPoints()
                        if (fiveCrownsViewModel.endgameCondition()) {
                            endGameNavigation = true
                        } else {
                            fiveCrownsViewModel.incrementWildCard()
                            fiveCrownsViewModel.incrementDealer()
                        }
                    },
                    updateExitGameDialogState = fiveCrownsViewModel::updateExitGameDialogState,
                    updateCalcDialogState = fiveCrownsViewModel::updateCalcDialogState,
                    reset = fiveCrownsViewModel::reset,
                    updatePotentialPoints = scoreTrackerViewModel::setPotentialPoints,
                    players = scoreTrackerViewModel.playerList,
                    state = fiveCrownsViewModel.state
                )
            }
        }
    }

    @Test
    fun back_press_chicken_test() {

        fiveCrownsScreenUtils.assertScreenShowing()
        assertFalse(backNavigation)

        // verify confirm button doesn't navigate
        fiveCrownsScreenUtils.clickBack()
        alertDialogUtils.assertShowing(
            title = "Leave game?",
            text = "If you leave you will lose game progress.",
            confirmButtonText = "Stay",
            dismissButtonText = "Exit"
        )
        alertDialogUtils.clickConfirmButton()
        alertDialogUtils.assertNotShowing()
        assertFalse(backNavigation)

        // verify dismiss button does navigate
        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.clickBack()
        alertDialogUtils.assertShowing(
            title = "Leave game?",
            text = "If you leave you will lose game progress.",
            confirmButtonText = "Stay",
            dismissButtonText = "Exit"
        )
        alertDialogUtils.clickDismissButton()
        alertDialogUtils.assertNotShowing()
        assertTrue(backNavigation)
    }

    @Test
    fun player_list_shows_correct_data_after_tally() {
        val intermediatePlayerData = listOf(
            Player("player1", 3, pendingPoints = 10),
            Player("player2", 15),
            Player("player3", 183),
        )
        val expectedPlayerData = listOf(
            Player("player1", 13),
            Player("player2", 15),
            Player("player3", 183),
        )

        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.assertPlayerData(initialPlayerData)
        assertTrue(fiveCrownsViewModel.state.dealer == 0)

        fiveCrownsScreenUtils.clickFirstCalculatorButton()
        fiveCrownsCalcDialogUtils.clickButton("10")
        fiveCrownsCalcDialogUtils.clickConfirmButton()

        fiveCrownsScreenUtils.assertPlayerData(intermediatePlayerData)

        fiveCrownsScreenUtils.clickTallyButton()
        fiveCrownsScreenUtils.assertPlayerData(expectedPlayerData)
        assertTrue(fiveCrownsViewModel.state.dealer == 1)
    }

    @Test
    fun calc_dialog_cancel_does_not_add_points() {
        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.clickFirstCalculatorButton()
        fiveCrownsScreenUtils.assertCalculatorShowing()
        fiveCrownsCalcDialogUtils.clickButton("K")

        fiveCrownsCalcDialogUtils.clickCancelButton()

        fiveCrownsScreenUtils.assertCalculatorNotShowing()
        fiveCrownsScreenUtils.assertPlayerData(initialPlayerData)
    }

    @Test
    fun wildcard_displays_correctly() {
        fiveCrownsScreenUtils.assertScreenShowing()

        fiveCrownsScreenUtils.clickTallyButton()
        fiveCrownsScreenUtils.assertWildCard("4")
    }

    @Test
    fun end_game_click() {
        fiveCrownsScreenUtils.assertScreenShowing()
        assertFalse(endGameNavigation)

        fiveCrownsScreenUtils.endTheGame()

        assertTrue(endGameNavigation)
    }
}