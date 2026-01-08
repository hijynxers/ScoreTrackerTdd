package com.grapevineindustries.fivecrowns.testutils

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import com.grapevineindustries.fivecrowns.FiveCrownsConstants
import com.grapevineindustries.fivecrowns.Player
import com.grapevineindustries.fivecrowns.ui.FiveCrownsScreenTestTags
import com.grapevineindustries.fivecrowns.ui.composables.AlertDialogTestTags
import com.grapevineindustries.fivecrowns.ui.composables.FiveCrownsCalcDialogTestTags

class FiveCrownsScreenTestUtils(
    private val composeTestRule: ComposeTestRule
) {
    fun assertScreenShowing(
        numPlayers: Int = FiveCrownsConstants.DEFAULT_NUM_PLAYERS
    ) {
        composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.WILD_CARD)
            .assertIsDisplayed()
            .assertTextEquals(FiveCrownsConstants.INITIAL_WILD_CARD.toString())

        composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.PLAYER_COLUMN)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(numPlayers)

        composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.TALLY_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
            .assertTextEquals("Tally")
    }

    fun assertPlayerData(playerData: List<Player>) {
        val playerNameNodes =
            composeTestRule.onAllNodesWithTag(FiveCrownsScreenTestTags.PLAYER_NAME)
        val playerScoreNodes =
            composeTestRule.onAllNodesWithTag(FiveCrownsScreenTestTags.PLAYER_SCORE)
        val playerPotentialPointsNodes = composeTestRule.onAllNodesWithTag(
            FiveCrownsScreenTestTags.PLAYER_POTENTIAL_POINTS,
            useUnmergedTree = true
        )

        playerData.forEachIndexed { index, player ->
            playerNameNodes[index].assertTextEquals(player.name)
            playerScoreNodes[index].assertTextEquals(player.score.toString())
            playerPotentialPointsNodes[index].assertTextEquals(player.pendingPoints.toString())
        }
    }

    fun assertCalculatorShowing() {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.CALC_DIALOG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.OK)
            .assertIsDisplayed()
    }

    fun assertCalculatorNotShowing() {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.CALC_DIALOG)
            .assertDoesNotExist()
    }

    fun assertEndGameDialogShowing() {
        composeTestRule.onNodeWithTag(AlertDialogTestTags.ALERT_DIALOG)
            .isDisplayed()
    }

    fun assertEndGameDialogNotShowing() {
        composeTestRule.onNodeWithTag(AlertDialogTestTags.ALERT_DIALOG)
            .assertDoesNotExist()
    }

    fun assertWildCard(expected: String) {
        composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.WILD_CARD)
            .assertIsDisplayed()
            .assertTextEquals(expected)
    }

    fun clickBack() {
        Espresso.pressBack()
    }

    fun clickFirstCalculatorButton() {
        composeTestRule.onAllNodesWithTag(FiveCrownsScreenTestTags.CALC_BUTTON)
            .onFirst()
            .performClick()
    }

    fun clickTallyButton() {
        composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.TALLY_BUTTON)
            .performClick()
    }

    fun endTheGame() {
        for (i in 3..13) {
            clickTallyButton()
            composeTestRule.onNodeWithTag(AlertDialogTestTags.CONFIRM_BUTTON)
                .performClick()
        }
    }

    fun clickEndGameTally(
        startWildCard: Int = 3
    ) {
        for (i in startWildCard..13) {
            composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.TALLY_BUTTON)
                .assertIsDisplayed()
                .performClick()
            composeTestRule.onNodeWithTag(AlertDialogTestTags.CONFIRM_BUTTON)
                .assertIsDisplayed()
                .performClick()
        }
    }

    fun advanceToLastRound() {
        for (i in 3..12) {
            composeTestRule.onNodeWithTag(FiveCrownsScreenTestTags.TALLY_BUTTON)
                .performClick()
            composeTestRule.onNodeWithTag(AlertDialogTestTags.CONFIRM_BUTTON)
                .performClick()
        }
    }
}