package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.scoretrackertdd.ui.composables.AlertDialogTestTags

object AlertDialogTestUtils {
    private lateinit var composeTestRule: ComposeTestRule

    fun setup(rule: ComposeTestRule) {
        composeTestRule = rule
    }

    fun assertShowing(
        title: String = "Title",
        text: String = "text",
        confirmButtonText: String = "OK",
        dismissButtonText: String? = null
    ) {
        composeTestRule.onNodeWithTag(AlertDialogTestTags.ALERT_DIALOG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(AlertDialogTestTags.TITLE)
            .assertIsDisplayed()
            .assertTextEquals(title)
        composeTestRule.onNodeWithTag(AlertDialogTestTags.TEXT)
            .assertIsDisplayed()
            .assertTextEquals(text)
        composeTestRule.onNodeWithTag(AlertDialogTestTags.CONFIRM_BUTTON, useUnmergedTree = true)
            .assertIsDisplayed()
            .onChild()
            .assertTextEquals(confirmButtonText)
        if (dismissButtonText == null) {
            composeTestRule.onNodeWithTag(AlertDialogTestTags.DISMISS_BUTTON)
                .assertDoesNotExist()
        } else {
            composeTestRule.onNodeWithTag(AlertDialogTestTags.DISMISS_BUTTON, useUnmergedTree = true)
                .assertIsDisplayed()
                .onChild()
                .assertTextEquals(dismissButtonText)
        }
    }

    fun clickConfirmButton() {
        composeTestRule.onNodeWithTag(AlertDialogTestTags.CONFIRM_BUTTON)
            .performClick()
    }

    fun clickDismissButton() {
        composeTestRule.onNodeWithTag(AlertDialogTestTags.DISMISS_BUTTON)
            .performClick()
    }
}