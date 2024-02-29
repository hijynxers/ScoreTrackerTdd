package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.FiveCrownsCalcDialog
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FiveCrownsCalcDialogUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        FiveCrownsCalcDialogTestUtils.setup(composeTestRule)
    }

    @Test
    fun clicking_buttons_add_K_to_score() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsCalcDialog(
                    closeWithPoints = {},
                    cancelDialog = {},
                    wildCard = 3
                )
            }
        }

        FiveCrownsCalcDialogTestUtils.assertShowing()

        FiveCrownsCalcDialogTestUtils.clickButton("K")
        FiveCrownsCalcDialogTestUtils.assertSum("13")
        FiveCrownsCalcDialogTestUtils.assertFactors("  K")
    }

    @Test
    fun clicking_buttons_add_to_score_K_wild() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsCalcDialog(
                    closeWithPoints = {},
                    cancelDialog = {},
                    wildCard = 13
                )
            }
        }

        FiveCrownsCalcDialogTestUtils.assertShowing()

        FiveCrownsCalcDialogTestUtils.clickButton("3")
        FiveCrownsCalcDialogTestUtils.assertSum("3")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3")

        FiveCrownsCalcDialogTestUtils.clickButton("4")
        FiveCrownsCalcDialogTestUtils.assertSum("7")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4")

        FiveCrownsCalcDialogTestUtils.clickButton("5")
        FiveCrownsCalcDialogTestUtils.assertSum("12")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5")

        FiveCrownsCalcDialogTestUtils.clickButton("6")
        FiveCrownsCalcDialogTestUtils.assertSum("18")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6")

        FiveCrownsCalcDialogTestUtils.clickButton("7")
        FiveCrownsCalcDialogTestUtils.assertSum("25")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7")

        FiveCrownsCalcDialogTestUtils.clickButton("8")
        FiveCrownsCalcDialogTestUtils.assertSum("33")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8")

        FiveCrownsCalcDialogTestUtils.clickButton("9")
        FiveCrownsCalcDialogTestUtils.assertSum("42")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9")

        FiveCrownsCalcDialogTestUtils.clickButton("10")
        FiveCrownsCalcDialogTestUtils.assertSum("52")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10")

        FiveCrownsCalcDialogTestUtils.clickButton("J")
        FiveCrownsCalcDialogTestUtils.assertSum("63")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J")

        FiveCrownsCalcDialogTestUtils.clickButton("Q")
        FiveCrownsCalcDialogTestUtils.assertSum("75")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q")

        FiveCrownsCalcDialogTestUtils.clickButton("K")
        FiveCrownsCalcDialogTestUtils.assertSum("95")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q *K")

        FiveCrownsCalcDialogTestUtils.clickButton("JOKE")
        FiveCrownsCalcDialogTestUtils.assertSum("145")
        FiveCrownsCalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q *K  JOKE")
    }
}