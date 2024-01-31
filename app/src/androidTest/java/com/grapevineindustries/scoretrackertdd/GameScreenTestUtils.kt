package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import com.grapevineindustries.scoretrackertdd.ui.GameScreenTestTags
import com.grapevineindustries.scoretrackertdd.ui.composables.CalcDialogTestTags
import com.grapevineindustries.scoretrackertdd.viewmodel.Player
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

    fun assertPlayerData(playerData: List<Player>) {
        val playerNameNodes = composeTestRule.onAllNodesWithTag(GameScreenTestTags.PLAYER_NAME)
        val playerScoreNodes = composeTestRule.onAllNodesWithTag(GameScreenTestTags.PLAYER_SCORE)

        playerData.forEachIndexed { index, player ->
            playerNameNodes[index].assertTextEquals(player.name)
            playerScoreNodes[index].assertTextEquals(player.score.toString())
        }
    }

    fun assertCalculatorShowing() {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.CALC_DIALOG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(CalcDialogTestTags.OK)
            .assertIsDisplayed()
    }

    fun assertCalculatorNotShowing() {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.CALC_DIALOG)
            .assertDoesNotExist()
    }

    fun clickBack() {
        Espresso.pressBack()
    }

    fun clickFirstCalculatorButton() {
        composeTestRule.onAllNodesWithTag(GameScreenTestTags.CALC_BUTTON)
            .onFirst()
            .performClick()
    }

    fun clickCalcDialogConfirmButton() {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.OK)
            .performClick()
    }

    fun initPlayerList(viewModel: PlayersViewModel, playerNames: List<Player>) {
        viewModel.createPlayersList(playerNames.size)
        playerNames.forEachIndexed { index, player ->
            viewModel.setName(index, player.name)
            viewModel.setScore(index, player.score)
        }
    }
}