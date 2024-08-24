package com.grapevineindustries.scoretrackertdd.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.utils.AddPlayersTestUtils
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddPlayersUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val viewModel = ScoreTrackerViewModel()
    private val startGameButtonClicked = MutableStateFlow(false)

    private val addPlayersUtils = AddPlayersTestUtils(composeTestRule)

    @Before
    fun setup() {
        viewModel.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)

        composeTestRule.setContent {
            AddPlayersScreen(
                players = viewModel.playerList,
                updatePlayerName = viewModel::setName,
                onBackPress = {},
                onStatGameClicked = {
                    startGameButtonClicked.update { true }
                }
            )
        }
    }

    @Test
    fun clicked_start_game_button() {
        assertFalse(startGameButtonClicked.value)

        addPlayersUtils.clickStartGame()
        assertTrue(startGameButtonClicked.value)
    }

    @Test
    fun input_is_recorded_in_text_field() {
        val expectedText = "name 1"
        addPlayersUtils.assertScreenShowing()

        addPlayersUtils.addAndAssertPlayerNameText(
            index = 0,
            text = expectedText
        )
        addPlayersUtils.addAndAssertPlayerNameText(
            index = 2,
            text = expectedText
        )
    }
}