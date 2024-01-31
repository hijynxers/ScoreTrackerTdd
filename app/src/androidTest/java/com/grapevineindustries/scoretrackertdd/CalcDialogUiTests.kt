package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.CalcDialog
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalcDialogUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                CalcDialog(
                    closeWithPoints = {},
                    cancelDialog = {}
                )
            }
        }
        CalcDialogTestUtils.setup(composeTestRule)
    }

    @Test
    fun clicking_buttons_add_to_score() {
        CalcDialogTestUtils.assertShowing()

        CalcDialogTestUtils.clickButton("3")
        CalcDialogTestUtils.assertSum("3")
        CalcDialogTestUtils.assertFactors("  3")

        CalcDialogTestUtils.clickButton("4")
        CalcDialogTestUtils.assertSum("7")
        CalcDialogTestUtils.assertFactors("  3  4")

        CalcDialogTestUtils.clickButton("5")
        CalcDialogTestUtils.assertSum("12")
        CalcDialogTestUtils.assertFactors("  3  4  5")

        CalcDialogTestUtils.clickButton("6")
        CalcDialogTestUtils.assertSum("18")
        CalcDialogTestUtils.assertFactors("  3  4  5  6")

        CalcDialogTestUtils.clickButton("7")
        CalcDialogTestUtils.assertSum("25")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7")

        CalcDialogTestUtils.clickButton("8")
        CalcDialogTestUtils.assertSum("33")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8")

        CalcDialogTestUtils.clickButton("9")
        CalcDialogTestUtils.assertSum("42")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9")

        CalcDialogTestUtils.clickButton("10")
        CalcDialogTestUtils.assertSum("52")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10")

        CalcDialogTestUtils.clickButton("J")
        CalcDialogTestUtils.assertSum("63")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J")

        CalcDialogTestUtils.clickButton("Q")
        CalcDialogTestUtils.assertSum("75")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q")

        CalcDialogTestUtils.clickButton("K")
        CalcDialogTestUtils.assertSum("88")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q  K")

        CalcDialogTestUtils.clickButton("JOKE")
        CalcDialogTestUtils.assertSum("138")
        CalcDialogTestUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q  K  JOKE")
    }
}