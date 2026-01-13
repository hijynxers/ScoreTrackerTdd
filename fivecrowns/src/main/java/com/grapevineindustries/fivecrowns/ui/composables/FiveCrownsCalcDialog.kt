package com.grapevineindustries.fivecrowns.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.grapevineindustries.common.R
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.data.FiveCrownsConstants

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FiveCrownsCalcDialogPreview() {
    ScoreTrackerTheme {
        FiveCrownsCalcDialog(
            closeWithPoints = {},
            cancelDialog = {},
            wildCard = 3,
            player = "Player 1"
        )
    }
}

private val numbers = listOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 50)

@Composable
fun FiveCrownsCalcDialog(
    closeWithPoints: (Int) -> Unit,
    cancelDialog: () -> Unit,
    wildCard: Int,
    player: String
) {
    val sum = rememberSaveable { mutableIntStateOf(0) }
    val factors = rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = cancelDialog
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = FiveCrownsConstants.CARD_ELEVATION
            ),
            content = {
                Column(
                    modifier = Modifier
                        .testTag(FiveCrownsCalcDialogTestTags.CALC_DIALOG)
                        .padding(all = 8.dp),
                    horizontalAlignment = AbsoluteAlignment.Right
                ) {
                    Text(
                        modifier = Modifier.testTag(FiveCrownsCalcDialogTestTags.PLAYER),
                        text = player,
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.testTag(FiveCrownsCalcDialogTestTags.FACTORS),
                        text = factors.value,
                        textAlign = TextAlign.Right
                    )
                    Text(
                        modifier = Modifier.testTag(FiveCrownsCalcDialogTestTags.SUM),
                        fontWeight = FontWeight.Bold,
                        text = sum.intValue.toString()
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3)
                    ) {
                        items(numbers) { element ->
                            val convertedElement = convertWildCard(element)
                            Button(
                                modifier = Modifier
                                    .testTag(FiveCrownsCalcDialogTestTags.BUTTON + convertedElement)
                                    .padding(4.dp),
                                onClick = {
                                    if (convertWildCard(wildCard) == convertedElement) {
                                        sum.intValue += 20
                                        factors.value += " *$convertedElement"
                                    } else {
                                        sum.intValue += element
                                        factors.value += "  $convertedElement"
                                    }
                                }
                            ) {
                                Text(
                                    text = convertedElement,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,  // horizontal center of the text
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 4.dp,
                                end = 4.dp,
                                top = 12.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Button(
                            modifier = Modifier
                                .testTag(FiveCrownsCalcDialogTestTags.CANCEL),
                            onClick = cancelDialog
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            modifier = Modifier
                                .testTag(FiveCrownsCalcDialogTestTags.OK)
                                .weight(1f),
                            onClick = { closeWithPoints(sum.intValue) }
                        ) {
                            Text(
                                text = stringResource(id = R.string.ok)
                            )
                        }
                    }

                }
            }
        )
    }
}

fun convertWildCard(card: Int): String {
    var ret = card.toString()
    when (card) {
        11 -> ret = "J"
        12 -> ret = "Q"
        13 -> ret = "K"
        50 -> ret = "JOKE"
    }
    return ret
}

object FiveCrownsCalcDialogTestTags {
    const val CALC_DIALOG = "CALC_DIALOG"
    const val OK = "CALC_DIALOG_OK"
    const val CANCEL = "CALC_DIALOG_CANCEL"
    const val BUTTON = "CALC_DIALOG_BUTTON_"
    const val SUM = "CALC_DIALOG_SUM"
    const val FACTORS = "CALC_DIALOG_FACTORS"
    const val PLAYER = "CALC_DIALOG_PLAYER"
}