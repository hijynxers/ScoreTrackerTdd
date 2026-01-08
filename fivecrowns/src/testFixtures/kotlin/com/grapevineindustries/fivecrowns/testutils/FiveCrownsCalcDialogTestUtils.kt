package com.grapevineindustries.fivecrowns.testutils

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.fivecrowns.ui.composables.FiveCrownsCalcDialogTestTags

class FiveCrownsCalcDialogTestUtils(
    private val composeTestRule: ComposeTestRule
) {

    private val buttonList = listOf("3","4","5","6","7","8","9","10","J","Q","K","JOKE")

    fun assertShowing() {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.CALC_DIALOG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.OK)
            .assertIsDisplayed()
            .assertTextEquals("OK")
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.SUM)
            .assertIsDisplayed()
            .assertTextEquals("0")
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.FACTORS)
            .assertIsNotDisplayed()

        buttonList.forEach { num ->
            composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.BUTTON + num)
                .assertIsDisplayed()
                .assertTextEquals(num)
        }
    }

    fun assertSum(sum: String) {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.SUM)
            .assertTextEquals(sum)
    }

    fun assertFactors(factors: String) {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.FACTORS)
            .assertTextEquals(factors)
    }

    fun assertPlayerName(name: String) {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.PLAYER)
            .assertIsDisplayed()
            .assertTextEquals(name)
    }

    fun clickConfirmButton() {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.OK)
            .performClick()
    }

    fun clickCancelButton() {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.CANCEL)
            .performClick()
    }

    fun clickButton(buttonNum: String) {
        composeTestRule.onNodeWithTag(FiveCrownsCalcDialogTestTags.BUTTON + buttonNum)
            .performClick()
    }
}