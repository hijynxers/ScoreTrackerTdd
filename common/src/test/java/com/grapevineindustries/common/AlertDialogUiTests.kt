package com.grapevineindustries.common

import ScoreTrackerAlertDialogTestUtils
import androidx.compose.ui.test.junit4.createComposeRule
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialog
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AlertDialogUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val alertDialogUtils = ScoreTrackerAlertDialogTestUtils(composeTestRule)

    @Test
    fun alert_dialog_displays_no_dismiss_button() {
        composeTestRule.setContent {
            ScoreTrackerTheme {
                ScoreTrackerAlertDialog(
                    title = "title",
                    text = "text",
                    confirmButtonText = "ok",
                    onConfirmClick = { }
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
        TestCase.assertTrue(buttonClicked)
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
        TestCase.assertFalse(confirmButtonClicked)
        TestCase.assertFalse(dismissButtonClicked)

        alertDialogUtils.assertShowing(
            title = "title",
            text = "text",
            confirmButtonText = "ok",
            dismissButtonText = "exit"
        )
        alertDialogUtils.clickConfirmButton()
        alertDialogUtils.clickDismissButton()
        TestCase.assertTrue(confirmButtonClicked)
        TestCase.assertTrue(dismissButtonClicked)
    }
}