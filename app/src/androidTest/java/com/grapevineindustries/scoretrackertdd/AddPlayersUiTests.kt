package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddPlayersUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val viewModel = PlayersViewModel()
    private val startGameButtonClicked = MutableStateFlow(false)

    @Before
    fun setup() {
        AddPlayersTestUtils.setup(composeTestRule)

        viewModel.createPlayersList(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)

        composeTestRule.setContent {
            AddPlayersScreen(
                players = viewModel.playerList,
                updatePlayerName = viewModel::setName,
                onStatGameClicked = {
                    startGameButtonClicked.update { true }
                }
            )
        }
    }

    @Test
    fun input_is_recorded_in_text_field() {
        val expectedText = "name 1"
        AddPlayersTestUtils.assertScreenShowing()

        AddPlayersTestUtils.addAndAssertPlayerNameText(
            index = 0,
            text = expectedText
        )
        AddPlayersTestUtils.addAndAssertPlayerNameText(
            index = 2,
            text = expectedText
        )
    }

    @Test
    fun game_start_makes_players_list() {
        AddPlayersTestUtils.assertScreenShowing()
        AddPlayersTestUtils.clickStartGame()

        assertTrue(startGameButtonClicked.value)
    }
}