package com.grapevineindustries.common.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.grapevineindustries.common.R
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialogTestTags.ALERT_DIALOG
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialogTestTags.CONFIRM_BUTTON
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialogTestTags.DISMISS_BUTTON
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialogTestTags.TEXT
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialogTestTags.TITLE
import com.grapevineindustries.common.theme.ScoreTrackerTheme

@Preview
@Composable
private fun ScoreTrackerAlertDialogPreview() {
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
    testTag: String = ALERT_DIALOG,
    title: String,
    text: String,
    confirmButtonText: String,
    dismissButtonText: String? = null
) {
    AlertDialog(
        modifier = Modifier.testTag(testTag),
        title = {
            Text(
                modifier = Modifier.testTag(TITLE),
                text = title
            )
        },
        text = {
            Text(
                modifier = Modifier.testTag(TEXT),
                text = text
            )
        },
        confirmButton = {
            STButton(
                onClick = onConfirmClick,
                testTag = CONFIRM_BUTTON,
                text = confirmButtonText
            )
        },
        dismissButton = {
            if (dismissButtonText != null) {
                STButton(
                    onClick = onDismissClick,
                    testTag = DISMISS_BUTTON,
                    text = dismissButtonText
                )
            }
        },
        onDismissRequest = onDismissClick
    )
}

object ScoreTrackerAlertDialogTestTags {
    const val ALERT_DIALOG = "ALERT_DIALOG"
    const val TITLE = "ALERT_DIALOG_TITLE"
    const val TEXT = "ALERT_DIALOG_TEXT"
    const val CONFIRM_BUTTON = "ALERT_DIALOG_CONFIRM_BUTTON"
    const val DISMISS_BUTTON = "ALERT_DIALOG_DISMISS_BUTTON"
}