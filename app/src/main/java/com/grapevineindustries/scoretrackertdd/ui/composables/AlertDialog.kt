package com.grapevineindustries.scoretrackertdd.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ScoreTrackerAlertDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    testTag: String = AlertDialogTestTags.ALERT_DIALOG,
    title: String,
    text: String,
    confirmButtonText: String,
    dismissButtonText: String? = null
) {
    AlertDialog(
        modifier = Modifier.testTag(testTag),
        title = {
            Text(
                modifier = Modifier.testTag(AlertDialogTestTags.TITLE),
                text = title
            )
        },
        text = {
            Text(
                modifier = Modifier.testTag(AlertDialogTestTags.TEXT),
                text = text
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier.testTag(AlertDialogTestTags.CONFIRM_BUTTON),
                onClick = onConfirmClick,
                content = {
                    Text(
                        text = confirmButtonText
                    )
                }
            )
        },
        dismissButton = {
            if (dismissButtonText != null) {
                 Button(
                    modifier = Modifier.testTag(AlertDialogTestTags.DISMISS_BUTTON),
                    onClick = onDismissClick,
                    content = {
                        Text(
                            text = dismissButtonText
                        )
                    }
                )
            }
        },
        onDismissRequest = onDismissRequest
    )
}

object AlertDialogTestTags {
    const val ALERT_DIALOG = "ALERT_DIALOG"
    const val TITLE = "ALERT_DIALOG_TITLE"
    const val TEXT = "ALERT_DIALOG_TEXT"
    const val CONFIRM_BUTTON = "ALERT_DIALOG_CONFIRM_BUTTON"
    const val DISMISS_BUTTON = "ALERT_DIALOG_DISMISS_BUTTON"
}