package com.grapevineindustries.scoretrackertdd.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.convertWildCard
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsScreenTestUtils
import com.grapevineindustries.scoretrackertdd.utils.AlertDialogTestUtils
import com.grapevineindustries.scoretrackertdd.utils.FiveCrownsCalcDialogTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FiveCrownsScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()
    private var backNavigation = false
    private var endGameNavigation = false

    private val fiveCrownsScreenUtils = FiveCrownsScreenTestUtils(composeTestRule)
    private val alertDialogUtils = AlertDialogTestUtils(composeTestRule)
    private val fiveCrownsCalcDialogUtils = FiveCrownsCalcDialogTestUtils(composeTestRule)

    private val initialPlayerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        val scoreTrackerViewModel = ScoreTrackerViewModel()
        fiveCrownsScreenUtils.initPlayerList(
            viewModel = scoreTrackerViewModel,
            playerNames = initialPlayerData
        )
        backNavigation = false

        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreen(
                    onCloseGame = { backNavigation = true },
                    onEndGameClick = { endGameNavigation = true },
                    tallyPoints = scoreTrackerViewModel::tallyPoints,
                    updatePotentialPoints = scoreTrackerViewModel::setPotentialPoints,
                    players = scoreTrackerViewModel.playerList
                )
            }
        }
    }

//    @Test
//    fun back_press_chicken_test() {
//        fiveCrownsScreenUtils.assertScreenShowing()
//        assertFalse(backNavigation)
//
//        // verify confirm button doesn't navigate
//        fiveCrownsScreenUtils.clickBack()
//        alertDialogUtils.assertShowing(
//            title = context.getString(R.string.leave_game),
//            text = context.getString(R.string.lose_game_progress),
//            confirmButtonText = context.getString(R.string.stay),
//            dismissButtonText = context.getString(R.string.exit)
//        )
//        alertDialogUtils.clickConfirmButton()
//        alertDialogUtils.assertNotShowing()
//        assertFalse(backNavigation)
//
//        // verify dismiss button does navigate
//        fiveCrownsScreenUtils.assertScreenShowing()
//        fiveCrownsScreenUtils.clickBack()
//        alertDialogUtils.assertShowing(
//            title = context.getString(R.string.leave_game),
//            text = context.getString(R.string.lose_game_progress),
//            confirmButtonText = context.getString(R.string.stay),
//            dismissButtonText = context.getString(R.string.exit)
//        )
//        alertDialogUtils.clickDismissButton()
//        alertDialogUtils.assertNotShowing()
//        assertTrue(backNavigation)
//    }

    @Test
    fun player_list_shows_correct_data() {
        fiveCrownsScreenUtils.assertScreenShowing()

        fiveCrownsScreenUtils.assertPlayerData(initialPlayerData)
    }

    @Test
    fun add_score() {
        fiveCrownsScreenUtils.assertScreenShowing()
        fiveCrownsScreenUtils.clickFirstCalculatorButton()

        fiveCrownsScreenUtils.assertCalculatorShowing()

        fiveCrownsCalcDialogUtils.clickButton("K")

        fiveCrownsCalcDialogUtils.clickConfirmButton()
        fiveCrownsScreenUtils.assertCalculatorNotShowing()
        var expectedPlayerData = listOf(
            Player("player1", 3, 13),
            Player("player2", 15),
            Player("player3", 183),
        )
        fiveCrownsScreenUtils.assertPlayerData(expectedPlayerData)

        fiveCrownsScreenUtils.clickTallyButton()
        fiveCrownsScreenUtils.assertWildCard("4")

        expectedPlayerData = listOf(
            Player("player1", 16),
            Player("player2", 15),
            Player("player3", 183),
        )
        fiveCrownsScreenUtils.assertPlayerData(expectedPlayerData)
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
        fiveCrownsScreenUtils.clickEndGameButton()
    }
}