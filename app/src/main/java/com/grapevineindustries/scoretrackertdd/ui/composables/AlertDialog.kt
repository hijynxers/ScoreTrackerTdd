package com.grapevineindustries.scoretrackertdd.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme

@Preview
@Composable
private fun AlertDialogPreview() {
    ScoreTrackerTheme {
        ScoreTrackerAlertDialog(
            onConfirmClick = { },
            onDismissClick = { },
            title = stringResource(R.string.leave_game),
            text = stringResource(R.string.lose_game_progress),
            confirmButtonText = stringResource(R.string.stay),
            dismissButtonText = stringResource(R.string.exit)
        )
    }
}

@Composable
fun ScoreTrackerAlertDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit = {},
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
            STButton(
                onClick = onConfirmClick,
                testTag = AlertDialogTestTags.CONFIRM_BUTTON,
                text = confirmButtonText
            )
        },
        dismissButton = {
            if (dismissButtonText != null) {
                STButton(
                    onClick = onDismissClick,
                    testTag = AlertDialogTestTags.DISMISS_BUTTON,
                    text = dismissButtonText
                )
            }
        },
        onDismissRequest = onDismissClick
    )
}

object AlertDialogTestTags {
    const val ALERT_DIALOG = "ALERT_DIALOG"
    const val TITLE = "ALERT_DIALOG_TITLE"
    const val TEXT = "ALERT_DIALOG_TEXT"
    const val CONFIRM_BUTTON = "ALERT_DIALOG_CONFIRM_BUTTON"
    const val DISMISS_BUTTON = "ALERT_DIALOG_DISMISS_BUTTON"
}