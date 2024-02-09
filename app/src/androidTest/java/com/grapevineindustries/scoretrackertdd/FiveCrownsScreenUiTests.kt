package com.grapevineindustries.scoretrackertdd

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.composables.convertWildCard
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsViewModel
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FiveCrownsScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()
    private var backNavigation = false

    private val context: Context = ApplicationProvider.getApplicationContext()

    private val initialPlayerData = listOf(
        Player("player1", 3),
        Player("player2", 15),
        Player("player3", 183),
    )

    @Before
    fun setup() {
        FiveCrownsScreenTestUtils.setup(composeTestRule)
        AlertDialogTestUtils.setup(composeTestRule)
        CalcDialogTestUtils.setup(composeTestRule)

        val playersViewModel = ScoreTrackerViewModel()

        FiveCrownsScreenTestUtils.initPlayerList(
            viewModel = playersViewModel,
            playerNames = initialPlayerData
        )

        val fiveCrownsViewModel = FiveCrownsViewModel()
        backNavigation = false

        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsScreen(
                    onCloseGame = { backNavigation = true },
                    players = playersViewModel.playerList,
                    exitGameDialogState = fiveCrownsViewModel.exitGameDialogState.collectAsState().value,
                    updateExitGameDialogState = fiveCrownsViewModel::updateExitGameDialogState,
                    updatePotentialPoints = playersViewModel::setPotentialPoints,
                    tallyPoints = {
                        playersViewModel.tallyPoints()
                        fiveCrownsViewModel.incrementWildCard()
                    },
                    wildCard = fiveCrownsViewModel.wildCard.collectAsState().value,
                    dealerIndex = fiveCrownsViewModel.dealer.collectAsState().value
                )
            }
        }
    }

    @Test
    fun back_press_chicken_test() {
        FiveCrownsScreenTestUtils.assertScreenShowing()
        assertFalse(backNavigation)

        // verify confirm button doesn't navigate
        FiveCrownsScreenTestUtils.clickBack()
        AlertDialogTestUtils.assertShowing(
            title = context.getString(R.string.leave_game),
            text = context.getString(R.string.lose_game_progress),
            confirmButtonText = context.getString(R.string.stay),
            dismissButtonText = context.getString(R.string.exit)
        )
        AlertDialogTestUtils.clickConfirmButton()
        AlertDialogTestUtils.assertNotShowing()
        assertFalse(backNavigation)

        // verify dismiss button does navigate
        FiveCrownsScreenTestUtils.assertScreenShowing()
        FiveCrownsScreenTestUtils.clickBack()
        AlertDialogTestUtils.assertShowing(
            title = context.getString(R.string.leave_game),
            text = context.getString(R.string.lose_game_progress),
            confirmButtonText = context.getString(R.string.stay),
            dismissButtonText = context.getString(R.string.exit)
        )
        AlertDialogTestUtils.clickDismissButton()
        AlertDialogTestUtils.assertNotShowing()
        assertTrue(backNavigation)
    }

    @Test
    fun player_list_shows_correct_data() {
        FiveCrownsScreenTestUtils.assertScreenShowing()

        FiveCrownsScreenTestUtils.assertPlayerData(initialPlayerData)
    }

    @Test
    fun add_score() {
        FiveCrownsScreenTestUtils.assertScreenShowing()
        FiveCrownsScreenTestUtils.clickFirstCalculatorButton()

        FiveCrownsScreenTestUtils.assertCalculatorShowing()

        CalcDialogTestUtils.clickButton("K")

        CalcDialogTestUtils.clickConfirmButton()
        FiveCrownsScreenTestUtils.assertCalculatorNotShowing()
        var expectedPlayerData = listOf(
            Player("player1", 3, 13),
            Player("player2", 15),
            Player("player3", 183),
        )
        FiveCrownsScreenTestUtils.assertPlayerData(expectedPlayerData)

        FiveCrownsScreenTestUtils.clickTallyButton()
        FiveCrownsScreenTestUtils.assertWildCard("4")

        expectedPlayerData = listOf(
            Player("player1", 16),
            Player("player2", 15),
            Player("player3", 183),
        )
        FiveCrownsScreenTestUtils.assertPlayerData(expectedPlayerData)
    }

    @Test
    fun calc_dialog_cancel_does_not_add_points() {
        FiveCrownsScreenTestUtils.assertScreenShowing()
        FiveCrownsScreenTestUtils.clickFirstCalculatorButton()
        FiveCrownsScreenTestUtils.assertCalculatorShowing()
        CalcDialogTestUtils.clickButton("K")

        CalcDialogTestUtils.clickCancelButton()

        FiveCrownsScreenTestUtils.assertCalculatorNotShowing()
        FiveCrownsScreenTestUtils.assertPlayerData(initialPlayerData)
    }

    @Test
    fun wildcard_displays_correctly() {
        FiveCrownsScreenTestUtils.assertScreenShowing()

        for (i in 4..13) {
            FiveCrownsScreenTestUtils.clickTallyButton()
            FiveCrownsScreenTestUtils.assertWildCard(convertWildCard(i))
        }
    }
}