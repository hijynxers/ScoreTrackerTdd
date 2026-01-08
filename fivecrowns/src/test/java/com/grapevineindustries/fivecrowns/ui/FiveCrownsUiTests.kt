package com.grapevineindustries.fivecrowns.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.FiveCrownsState
import com.grapevineindustries.fivecrowns.GameViewModel
import com.grapevineindustries.fivecrowns.Player
import com.grapevineindustries.fivecrowns.ui.composables.AlertDialogTestTags
import com.grapevineindustries.fivecrowns.utils.AlertDialogTestUtils
import com.grapevineindustries.fivecrowns.utils.FiveCrownsCalcDialogTestUtils
import com.grapevineindustries.fivecrowns.utils.FiveCrownsScreenTestUtils
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FiveCrownsUiTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fiveCrownsScreenUtils = FiveCrownsScreenTestUtils(composeTestRule)
    private val alertDialogUtils = AlertDialogTestUtils(composeTestRule)
    private val fiveCrownsCalcDialogUtils = FiveCrownsCalcDialogTestUtils(composeTestRule)

    private val initialPlayerData = listOf(
        Player("player1"),
        Player("player2"),
        Player("player3"),
    )

    private var gameViewModel = GameViewModel()

    private var backNavigation = false
    private var finalScoreNavigation = false
    private var isCalcDialogShown = false
    private var tallyPointsClicked = false
    private var updatePlayerClicked = false
    private var lastClickedIndex = mutableIntStateOf(-1)

    @Before
    fun setup() {
        backNavigation = false
        finalScoreNavigation = false
        isCalcDialogShown = false
        tallyPointsClicked = false
        updatePlayerClicked = false
        lastClickedIndex.intValue = -1
        gameViewModel.createPlayersList(3)
        gameViewModel.updatePlayer(0, Player("player1"))
        gameViewModel.updatePlayer(1, Player("player2"))
        gameViewModel.updatePlayer(2, Player("player3"))
    }

    @Test
    fun tally_points_clicked() {
        launchScreenContent()
        fiveCrownsScreenUtils.assertScreenShowing()
        assertFalse(tallyPointsClicked)

        fiveCrownsScreenUtils.clickTallyButton()

        assertTrue(tallyPointsClicked)
    }

    @Test
    fun show_calc_dialog_clicked() {
        launchScreenContent()
        fiveCrownsScreenUtils.assertScreenShowing()
        assertFalse(isCalcDialogShown)
        assert(lastClickedIndex.intValue == -1)

        fiveCrownsScreenUtils.clickFirstCalculatorButton()

        assertTrue(isCalcDialogShown)
        assert(lastClickedIndex.intValue == 0)
    }

    @Test
    fun verify_initial_state() {
        launchScreenContent()
        fiveCrownsScreenUtils.assertCalculatorNotShowing()
        fiveCrownsScreenUtils.assertEndGameDialogNotShowing()
        fiveCrownsScreenUtils.assertWildCard("3")
        // Is there a way to test dealer position from the color?
    }

    @Test
    fun verify_exit_game_dialog_state() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreenContent(
                    players = listOf(
                        Player("player1"),
                        Player("player2"),
                        Player("player3")
                    ),
                    showCalcDialog = { isCalcDialogShown = true },
                    tallyPoints = { tallyPointsClicked = true },
                    lastClickedIndex = lastClickedIndex,
                    state = FiveCrownsState(
                        wildCard = 13,
                        dealer = 0,
                        isExitGameDialogShowing = true,
                        isCalcDialogShowing = isCalcDialogShown
                    )
                )
            }
        }

        fiveCrownsScreenUtils.assertCalculatorNotShowing()
        fiveCrownsScreenUtils.assertEndGameDialogShowing()
        fiveCrownsScreenUtils.assertWildCard("K")
    }

    @Test
    fun verify_calc_dialog_state() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreenContent(
                    players = listOf(
                        Player("player1"),
                        Player("player2"),
                        Player("player3")
                    ),
                    showCalcDialog = { isCalcDialogShown = true },
                    tallyPoints = { tallyPointsClicked = true },
                    lastClickedIndex = lastClickedIndex,
                    state = FiveCrownsState(
                        wildCard = 12,
                        dealer = 0,
                        isExitGameDialogShowing = false,
                        isCalcDialogShowing = true
                    )
                )
            }
        }

        fiveCrownsScreenUtils.assertCalculatorNotShowing()
        fiveCrownsScreenUtils.assertEndGameDialogShowing()
        fiveCrownsScreenUtils.assertWildCard("Q")
    }

    @Test
    fun back_press_chicken_test() {
        launchScreen()
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
    fun no_score_show_alert_dialog_dismiss_clicked() {
        launchScreen()

        assertEquals(gameViewModel.state.value.wildCard, 3)
        assertFalse(gameViewModel.state.value.isNoScoreDialogShowing)
        fiveCrownsScreenUtils.clickTallyButton()

        assertTrue(gameViewModel.state.value.isNoScoreDialogShowing)
        assertEquals(gameViewModel.state.value.wildCard, 3)

        alertDialogUtils.assertShowing(
            title = "Tally scores?",
            text = "No points were entered. Are you sure you want to tally?",
            confirmButtonText = "Yes",
            dismissButtonText = "No"
        )

        alertDialogUtils.clickDismissButton()
        assertEquals(gameViewModel.state.value.wildCard, 3)
        fiveCrownsScreenUtils.assertPlayerData(
            playerData = listOf(
                Player("player1"),
                Player("player2"),
                Player("player3")
            )
        )
    }

    @Test
    fun no_score_show_alert_dialog_confirm_clicked_zero_score() {
        launchScreen()

        assertEquals(gameViewModel.state.value.wildCard, 3)
        assertFalse(gameViewModel.state.value.isNoScoreDialogShowing)
        fiveCrownsScreenUtils.clickTallyButton()

        assertTrue(gameViewModel.state.value.isNoScoreDialogShowing)
        assertEquals(gameViewModel.state.value.wildCard, 3)
        assertEquals(
            listOf(
                Player("player1"),
                Player("player2"),
                Player("player3")
            ),
            gameViewModel.players.toList()
        )

        alertDialogUtils.assertShowing(
            title = "Tally scores?",
            text = "No points were entered. Are you sure you want to tally?",
            confirmButtonText = "Yes",
            dismissButtonText = "No"
        )

        alertDialogUtils.clickConfirmButton()
        assertEquals(gameViewModel.state.value.wildCard, 4)
        fiveCrownsScreenUtils.assertPlayerData(
            playerData = listOf(
                Player("player1"),
                Player("player2"),
                Player("player3")
            )
        )
    }

    @Test
    fun no_score_show_alert_dialog_confirm_clicked_zero_score_and_endgame() {
        launchScreen()
        fiveCrownsScreenUtils.advanceToLastRound()

        assertEquals(gameViewModel.state.value.wildCard, 13)
        assertFalse(gameViewModel.state.value.isNoScoreDialogShowing)
        fiveCrownsScreenUtils.clickTallyButton()

        assertTrue(gameViewModel.state.value.isNoScoreDialogShowing)
        assertEquals(gameViewModel.state.value.wildCard, 13)
        assertEquals(
            listOf(
                Player("player1"),
                Player("player2"),
                Player("player3")
            ),
            gameViewModel.players.toList()
        )

        alertDialogUtils.assertShowing(
            title = "Tally scores?",
            text = "No points were entered. Are you sure you want to tally?",
            confirmButtonText = "Yes",
            dismissButtonText = "No"
        )

        assertFalse(finalScoreNavigation)

        alertDialogUtils.clickConfirmButton()
        assertTrue(finalScoreNavigation)
    }

    @Test
    fun player_list_shows_correct_data_after_tally() {
        launchScreen()

        val intermediatePlayerData = listOf(
            Player("player1", 0, pendingPoints = 10),
            Player("player2", 0),
            Player("player3", 0),
        )
        val expectedPlayerData = listOf(
            Player("player1", 10),
            Player("player2"),
            Player("player3"),
        )

        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.assertPlayerData(initialPlayerData)
        assertTrue(gameViewModel.state.value.dealer == 0)

        fiveCrownsScreenUtils.clickFirstCalculatorButton()
        fiveCrownsCalcDialogUtils.clickButton("10")
        fiveCrownsCalcDialogUtils.clickConfirmButton()

        fiveCrownsScreenUtils.assertPlayerData(intermediatePlayerData)

        fiveCrownsScreenUtils.clickTallyButton()
        fiveCrownsScreenUtils.assertPlayerData(expectedPlayerData)
        assertTrue(gameViewModel.state.value.dealer == 1)
    }

    @Test
    fun calc_dialog_cancel_does_not_add_points() {
        launchScreen()
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
        launchScreen()
        fiveCrownsScreenUtils.assertScreenShowing()

        fiveCrownsScreenUtils.clickTallyButton()
        composeTestRule.onNodeWithTag(AlertDialogTestTags.CONFIRM_BUTTON)
            .performClick()
        fiveCrownsScreenUtils.assertWildCard("4")
    }

    @Test
    fun end_game_click() {
        launchScreen()
        fiveCrownsScreenUtils.assertScreenShowing()
        assertFalse(finalScoreNavigation)

        fiveCrownsScreenUtils.endTheGame()

        assertTrue(finalScoreNavigation)
    }

    private fun launchScreen() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreen(
                    gameViewModel = gameViewModel,
                    navigateToLandingScreen = { backNavigation = true },
                    navigateToFinalScoreScreen = { finalScoreNavigation = true }
                )
            }
        }
    }

    private fun launchScreenContent() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreenContent(
                    players = listOf(
                        Player("player1"),
                        Player("player2"),
                        Player("player3")
                    ),
                    showCalcDialog = { isCalcDialogShown = true },
                    tallyPoints = { tallyPointsClicked = true },
                    lastClickedIndex = lastClickedIndex,
                    state = FiveCrownsState(
                        wildCard = 3,
                        dealer = 0,
                        isExitGameDialogShowing = false,
                        isCalcDialogShowing = isCalcDialogShown
                    )
                )
            }
        }
    }
}