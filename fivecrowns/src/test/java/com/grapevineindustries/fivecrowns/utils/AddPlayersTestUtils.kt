package com.grapevineindustries.fivecrowns.utils

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.grapevineindustries.fivecrowns.FiveCrownsConstants
import com.grapevineindustries.fivecrowns.ui.AddPlayersScreenTestTags

class AddPlayersTestUtils(
    private val composeTestRule: ComposeTestRule
) {
    fun assertScreenShowing(
        numPlayers: Int = FiveCrownsConstants.DEFAULT_NUM_PLAYERS
    ) {
        if(numPlayers != 0) {
            composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.PLAYER_COLUMN)
                .assertIsDisplayed()
                .onChildren()
                .assertCountEquals(numPlayers)
        } else {
            composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.PLAYER_COLUMN)
                .assertDoesNotExist()
        }

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

    fun addPlayerNameText(index: Int, text: String) {
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.PLAYER_TEXT_INPUT + index.toString())
            .assertIsDisplayed()
            .performTextInput(text)
    }

    fun clickStartGame() {
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.START_GAME_BUTTON).performClick()
    }
}