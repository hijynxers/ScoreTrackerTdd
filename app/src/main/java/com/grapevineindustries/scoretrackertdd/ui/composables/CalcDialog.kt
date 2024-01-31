package com.grapevineindustries.scoretrackertdd.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme

@Preview
@Composable
fun CalcDialogPreview() {
    ScoreTrackerTheme {
        CalcDialog(
            closeCalcDialog = {}
        )
    }
}

@Composable
fun CalcDialog(
    closeCalcDialog: () -> Unit
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = FiveCrownsConstants.CARD_ELEVATION
            ),
            content = {
                Column(
                    modifier = Modifier.testTag(CalcDialogTestTags.CALC_DIALOG)
                ) {
                    Button(
                        modifier = Modifier.testTag(CalcDialogTestTags.OK),
                        onClick = closeCalcDialog
                    ) {
                        Text(
                            text = "OK"
                        )
                    }
                }
            }
        )
    }
}

object CalcDialogTestTags {
    const val CALC_DIALOG = "CALC_DIALOG"
    const val OK = "CALC_DIALOG_OK"
}