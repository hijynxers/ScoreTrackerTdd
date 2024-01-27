package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.Espresso
import com.grapevineindustries.scoretrackertdd.ui.GameScreenTestTags
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayersViewModel

object GameScreenTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    fun assertScreenShowing(
        numPlayers: Int = FiveCrownsConstants.DEFAULT_NUM_PLAYERS
    ) {
        composeTestRule.onNodeWithTag(GameScreenTestTags.GAME_SCREEN)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(GameScreenTestTags.WILD_CARD)
            .assertIsDisplayed()
            .assertTextEquals(FiveCrownsConstants.INITIAL_WILD_CARD.toString())

        composeTestRule.onNodeWithTag(GameScreenTestTags.PLAYER_COLUMN)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(numPlayers)

        composeTestRule.onNodeWithTag(GameScreenTestTags.TALLY_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertTextEquals("Tally")
    }

    fun assertPlayerNames(playerNames: List<String>) {
        val playerNameNodes = composeTestRule.onNodeWithTag(GameScreenTestTags.PLAYER_COLUMN, useUnmergedTree = true)
            .assertIsDisplayed()
            .onChildren()
            .filter(hasTestTag(GameScreenTestTags.PLAYER_NAME))

        playerNames.forEachIndexed { index, name ->
            playerNameNodes[index].assertTextEquals(name)
        }

    }

    fun clickBack() {
        Espresso.pressBack()
    }

    fun initPlayerList(viewModel: PlayersViewModel, playerNames: List<String>) {
        viewModel.createPlayersList(playerNames.size)
        playerNames.forEachIndexed { index, name ->
            viewModel.setName(index, name)
        }
    }
}