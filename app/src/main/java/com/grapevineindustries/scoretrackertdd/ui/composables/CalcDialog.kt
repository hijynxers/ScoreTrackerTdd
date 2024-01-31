package com.grapevineindustries.scoretrackertdd.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.theme.Purple80
import com.grapevineindustries.scoretrackertdd.theme.PurpleGrey40
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
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
//    AlertDialog(
//        modifier = Modifier
//            .testTag(CalcDialogTestTags.CALC_DIALOG)
//            .padding(35.dp),
//        onDismissRequest = closeCalcDialog,
//        title = {
//            Text(
//                text = "OK"
//            )
//        },
//        text = {
//            val numbers = listOf(3,4,5,6,7,8,9,10,11,12,13,20,50)
//
//            Column {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(3)
//                ) {
//                    items(5) { element ->
//                        val convertedElement = 4
//                        Button(
//                            onClick = {
////                                sum.value = sum.value + element
////                                calc.value = calc.value + "  $convertedElement"
//                            },
//                            modifier = Modifier.padding(4.dp)
//                        ) {
//                            Text(
//                                text = "  $convertedElement",
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                    }
//                }
//            }
//
//        },
//        confirmButton = {
//            Text(
//                text = "Enter"
//            )
//        }
//
//
//    )
//    {
//        Button(
//            modifier = Modifier.testTag(CalcDialogTestTags.OK),
//            onClick = closeCalcDialog
//        ) {
//            Text(
//                text = "OK"
//            )
//        }
//    }
}

@Composable
fun ShowDialog(
    onDismiss: () -> Unit,
//    onAccept: (Int, Int) -> Unit,
    index: Int
) {
    Dialog(onDismissRequest = onDismiss) {
        CustomDialogUI(
            onDismiss = onDismiss,
//            onAccept = onAccept,
            index = index
        )
    }
}

@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
//    onAccept: (Int, Int) -> Unit,
    index: Int
){
    val sum = remember { mutableStateOf(0)  }
    val calc = remember { mutableStateOf("")  }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier
                .background(PurpleGrey40)
                .padding(horizontal = 8.dp)
        ) {

            //.......................................................................
            Text(
                text = calc.value,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = sum.value.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            val numbers = listOf(3,4,5,6,7,8,9,10,11,12,13,20,50)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(numbers) { element ->
                    val convertedElement = 4
                    Button(
                        onClick = {
                            sum.value = sum.value + element
                            calc.value = calc.value + "  $convertedElement"
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = "  $convertedElement",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        //.......................................................................
        Row(
            Modifier
                .fillMaxWidth()
                .background(Purple80),
            horizontalArrangement = Arrangement.SpaceAround) {
            TextButton(onClick = {
//                onAccept(index, sum.value)
                onDismiss()
            }) {
                Text(
                    text = "Continue",
                    color = Color.Black,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

object CalcDialogTestTags {
    const val CALC_DIALOG = "CALC_DIALOG"
    const val OK = "CALC_DIALOG_OK"
}