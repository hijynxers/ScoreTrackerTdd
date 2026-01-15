package com.grapevineindustries.fivecrowns.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HighLowScoreScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLaunched_thenRootAndPlaceholderAreDisplayed() {
        launchWithCompose()

        composeTestRule.onNodeWithTag(HighLowScoreScreenTestTags.ROOT)
            .assertIsDisplayed()
    }

    private fun launchWithCompose() {
        composeTestRule.setContent {
            HighLowScoreScreen()
        }
    }
}

