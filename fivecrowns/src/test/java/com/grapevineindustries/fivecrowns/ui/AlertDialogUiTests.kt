package com.grapevineindustries.fivecrowns.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.ui.composables.ScoreTrackerAlertDialog
import com.grapevineindustries.scoretrackertdd.utils.AlertDialogTestUtils
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AlertDialogUiTests {
    @JvmField
    @Rule
    val composeTestRule = createComposeRule()

    private val alertDialogUtils = AlertDialogTestUtils(composeTestRule)

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

        alertDialogUtils.assertShowing(
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

        alertDialogUtils.assertShowing(
            title = "title",
            text = "text",
            confirmButtonText = "ok",
            dismissButtonText = "exit"
        )
        alertDialogUtils.clickDismissButton()
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

        alertDialogUtils.assertShowing(
            title = "title",
            text = "text",
            confirmButtonText = "ok",
            dismissButtonText = "exit"
        )
        alertDialogUtils.clickConfirmButton()
        alertDialogUtils.clickDismissButton()
        assertTrue(confirmButtonClicked)
        assertTrue(dismissButtonClicked)
    }
}