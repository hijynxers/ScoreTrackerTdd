package com.grapevineindustries.scoretrackertdd

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.ScoreTrackerAlertDialog
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlertDialogUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        AlertDialogTestUtils.setup(composeTestRule)
    }

    @Test
    fun alert_dialog_displays_no_dismiss_button() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                ScoreTrackerAlertDialog(
                    title = "title",
                    text = "text",
                    confirmButtonText = "ok",
                    onConfirmClick = {  }
                )
            }
        }

        AlertDialogTestUtils.assertShowing(
            title = "title",
            text = "text",
            confirmButtonText = "ok"
        )
    }

    @Test
    fun alert_dialog_with_dismiss_button() {
        var buttonClicked = false
        composeTestRule.setContent {
            ScoreTrackerTheme {
                ScoreTrackerAlertDialog(
                    title = "title",
                    text = "text",
                    confirmButtonText = "ok",
                    dismissButtonText = "exit",
                    onConfirmClick = {},
                    onDismissClick = { buttonClicked = true }
                )
            }
        }

        AlertDialogTestUtils.assertShowing(
            title = "title",
            text = "text",
            confirmButtonText = "ok",
            dismissButtonText = "exit"
        )
        AlertDialogTestUtils.clickDismissButton()
        assertTrue(buttonClicked)
    }

    @Test
    fun button_clicks() {
        var confirmButtonClicked = false
        var dismissButtonClicked = false

        composeTestRule.setContent {
            ScoreTrackerTheme {
                ScoreTrackerAlertDialog(
                    title = "title",
                    text = "text",
                    confirmButtonText = "ok",
                    dismissButtonText = "exit",
                    onConfirmClick = { confirmButtonClicked = true },
                    onDismissClick = { dismissButtonClicked = true }
                )
            }
        }
        assertFalse(confirmButtonClicked)
        assertFalse(dismissButtonClicked)

        AlertDialogTestUtils.assertShowing(
            title = "title",
            text = "text",
            confirmButtonText = "ok",
            dismissButtonText = "exit"
        )
        AlertDialogTestUtils.clickConfirmButton()
        AlertDialogTestUtils.clickDismissButton()
        assertTrue(confirmButtonClicked)
        assertTrue(dismissButtonClicked)
    }
}