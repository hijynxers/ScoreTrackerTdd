package com.grapevineindustries.scoretrackertdd.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.utils.AlertDialogTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsCalcDialogTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsState
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsViewModel
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayerViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FiveCrownsUiTests {
    @JvmField
    @Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fiveCrownsScreenUtils = FiveCrownsScreenTestUtils(composeTestRule)
    private val alertDialogUtils = AlertDialogTestUtils(composeTestRule)
    private val fiveCrownsCalcDialogUtils = FiveCrownsCalcDialogTestUtils(composeTestRule)

    private val initialPlayerData = listOf(
        Player("player1"),
        Player("player2"),
        Player("player3"),
    )

    private var fiveCrownsViewModel = FiveCrownsViewModel()
    private var playerViewModel = PlayerViewModel()

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
        playerViewModel.createPlayersList(3)
        playerViewModel.updatePlayer(0, Player("player1"))
        playerViewModel.updatePlayer(1, Player("player2"))
        playerViewModel.updatePlayer(2, Player("player3"))
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
        assertTrue(fiveCrownsViewModel.state.value.dealer == 0)

        fiveCrownsScreenUtils.clickFirstCalculatorButton()
        fiveCrownsCalcDialogUtils.clickButton("10")
        fiveCrownsCalcDialogUtils.clickConfirmButton()

        fiveCrownsScreenUtils.assertPlayerData(intermediatePlayerData)

        fiveCrownsScreenUtils.clickTallyButton()
        fiveCrownsScreenUtils.assertPlayerData(expectedPlayerData)
        assertTrue(fiveCrownsViewModel.state.value.dealer == 1)
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
                    playerViewModel = playerViewModel,
                    fiveCrownsViewModel = fiveCrownsViewModel,
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
                    showCalcDialog = {
                        isCalcDialogShown = true
                    },
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