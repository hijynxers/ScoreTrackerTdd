package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class MainScreenUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Test
    fun firstTest() {
        composeTestRule.setContent {
            MainScreen()
        }

        composeTestRule.onNodeWithTag(MainScreenTestTags.TITLE).assertIsDisplayed()
    }




}