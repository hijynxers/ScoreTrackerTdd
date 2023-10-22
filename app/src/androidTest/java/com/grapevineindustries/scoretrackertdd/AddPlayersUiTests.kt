package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddPlayersUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        AddPlayersTestUtils.setup(composeTestRule)
        composeTestRule.setContent {
            AddPlayersScreen()
        }
    }

    @Test
    fun screenDisplays() {
        composeTestRule.onNodeWithTag(AddPlayersScreenTestTags.TestTag)
            .assertIsDisplayed()
    }

}