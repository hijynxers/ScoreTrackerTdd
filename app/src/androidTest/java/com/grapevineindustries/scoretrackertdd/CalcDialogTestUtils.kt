package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.scoretrackertdd.ui.composables.CalcDialogTestTags

object CalcDialogTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    private val buttonList = listOf("3","4","5","6","7","8","9","10","J","Q","K","JOKE")

    fun assertShowing() {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.CALC_DIALOG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(CalcDialogTestTags.OK)
            .assertIsDisplayed()
            .assertTextEquals("OK")
        composeTestRule.onNodeWithTag(CalcDialogTestTags.SUM)
            .assertIsDisplayed()
            .assertTextEquals("0")
        composeTestRule.onNodeWithTag(CalcDialogTestTags.FACTORS)
            .assertIsNotDisplayed()

        buttonList.forEachIndexed { index, num ->
            composeTestRule.onNodeWithTag(CalcDialogTestTags.BUTTON + buttonList[index])
                .assertIsDisplayed()
                .assertTextEquals(buttonList[index])
        }
    }

    fun assertSum(sum: String) {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.SUM)
            .assertTextEquals(sum)
    }

    fun assertFactors(factors: String) {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.FACTORS)
            .assertTextEquals(factors)
    }

    fun clickCalcDialogConfirmButton() {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.OK)
            .performClick()
    }

    fun clickButton(buttonNum: String) {
        composeTestRule.onNodeWithTag(CalcDialogTestTags.BUTTON + buttonNum)
            .performClick()
    }


}