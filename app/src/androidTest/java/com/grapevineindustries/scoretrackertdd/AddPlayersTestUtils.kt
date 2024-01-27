package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreenTestTags

object AddPlayersTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    fun assertScreenShowing(
        numPlayers: Int = FiveCrownsConstants.DEFAULT_NUM_PLAYERS
    ) {
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.ADD_PLAYERS_SCREEN)
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.PLAYER_COLUMN)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(numPlayers)

        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.START_GAME_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertTextContains("Start Game")
    }

    fun addAndAssertPlayerNameText(index: Int, text: String) {
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.PLAYER_TEXT_INPUT + index.toString())
            .assertIsDisplayed()
            .performTextInput(text)
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.PLAYER_TEXT_INPUT + index.toString())
            .assertIsDisplayed()
            .assert(hasText(text))
    }
}