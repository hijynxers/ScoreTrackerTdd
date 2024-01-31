package com.grapevineindustries.scoretrackertdd

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
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

    private val context: Context = ApplicationProvider.getApplicationContext()

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
                    tallyPoints = {
                        playersViewModel.tallyPoints()
                        gameViewModel.incrementWildCard()
                    },
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
            title = context.getString(R.string.leave_game),
            text = context.getString(R.string.lose_game_progress),
            confirmButtonText = context.getString(R.string.stay),
            dismissButtonText = context.getString(R.string.exit)
        )
        AlertDialogTestUtils.clickConfirmButton()
        AlertDialogTestUtils.assertNotShowing()
        assertFalse(backNavigation)

        // verify dismiss button does navigate
        GameScreenTestUtils.assertScreenShowing()
        GameScreenTestUtils.clickBack()
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
            .assertTextEquals(context.getString(R.string.wildcard, "4"))
        expectedPlayerData = listOf(
            Player("player1", 16),
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
            assertWildCard(context.getString(R.string.wildcard, convertWildCard(i)))
        }
    }
}