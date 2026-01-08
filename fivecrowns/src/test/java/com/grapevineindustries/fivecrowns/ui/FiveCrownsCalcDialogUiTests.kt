package com.grapevineindustries.fivecrowns.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.ui.composables.FiveCrownsCalcDialog
import com.grapevineindustries.fivecrowns.testutils.FiveCrownsCalcDialogTestUtils
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FiveCrownsCalcDialogUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fiveCrownsCalcUtils = FiveCrownsCalcDialogTestUtils(composeTestRule)

    private fun launchWithCompose(
        wildcard: Int
    ) {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                FiveCrownsCalcDialog(
                    closeWithPoints = {},
                    cancelDialog = {},
                    wildCard = wildcard,
                    player = "Player 1"
                )
            }
        }
    }

    @Test
    fun clicking_buttons_add_K_to_score() {
        launchWithCompose(3)
        fiveCrownsCalcUtils.assertShowing()
        fiveCrownsCalcUtils.assertPlayerName("Player 1")

        fiveCrownsCalcUtils.clickButton("K")
        fiveCrownsCalcUtils.assertSum("13")
        fiveCrownsCalcUtils.assertFactors("  K")
    }

    @Test
    fun clicking_buttons_add_to_score_K_wild() {
        launchWithCompose(13)

        fiveCrownsCalcUtils.assertShowing()

        fiveCrownsCalcUtils.clickButton("3")
        fiveCrownsCalcUtils.assertSum("3")
        fiveCrownsCalcUtils.assertFactors("  3")

        fiveCrownsCalcUtils.clickButton("4")
        fiveCrownsCalcUtils.assertSum("7")
        fiveCrownsCalcUtils.assertFactors("  3  4")

        fiveCrownsCalcUtils.clickButton("5")
        fiveCrownsCalcUtils.assertSum("12")
        fiveCrownsCalcUtils.assertFactors("  3  4  5")

        fiveCrownsCalcUtils.clickButton("6")
        fiveCrownsCalcUtils.assertSum("18")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6")

        fiveCrownsCalcUtils.clickButton("7")
        fiveCrownsCalcUtils.assertSum("25")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7")

        fiveCrownsCalcUtils.clickButton("8")
        fiveCrownsCalcUtils.assertSum("33")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8")

        fiveCrownsCalcUtils.clickButton("9")
        fiveCrownsCalcUtils.assertSum("42")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8  9")

        fiveCrownsCalcUtils.clickButton("10")
        fiveCrownsCalcUtils.assertSum("52")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8  9  10")

        fiveCrownsCalcUtils.clickButton("J")
        fiveCrownsCalcUtils.assertSum("63")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8  9  10  J")

        fiveCrownsCalcUtils.clickButton("Q")
        fiveCrownsCalcUtils.assertSum("75")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q")

        fiveCrownsCalcUtils.clickButton("K")
        fiveCrownsCalcUtils.assertSum("95")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q *K")

        fiveCrownsCalcUtils.clickButton("JOKE")
        fiveCrownsCalcUtils.assertSum("145")
        fiveCrownsCalcUtils.assertFactors("  3  4  5  6  7  8  9  10  J  Q *K  JOKE")
    }
}