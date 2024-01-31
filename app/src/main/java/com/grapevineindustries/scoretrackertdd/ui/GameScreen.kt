package com.grapevineindustries.scoretrackertdd.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.CalcDialog
import com.grapevineindustries.scoretrackertdd.ui.composables.ScoreTrackerAlertDialog
import com.grapevineindustries.scoretrackertdd.ui.composables.ShowDialog
import com.grapevineindustries.scoretrackertdd.viewmodel.Player

@Preview
@Composable
fun GameScreenPreview() {

    ScoreTrackerTheme {
        GameScreenContent(
            players = listOf(
                Player("player 1", 0),
                Player("player 2", 12),
                Player("player 3", 145),
            ),
            showCalcDialog = {}
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(
    onCloseGame: () -> Unit,
    players: SnapshotStateList<Player>,
    exitGameDialogState: Boolean,
    updateExitGameDialogState: (Boolean) -> Unit
) {
    val calcDialogState = remember { mutableStateOf(false) }

    if (exitGameDialogState) {
        ScoreTrackerAlertDialog(
            onConfirmClick = { updateExitGameDialogState(false) },
            onDismissClick = {
                updateExitGameDialogState(false)

                onCloseGame()
            },
            title = "Are you sure you want to quit?",
            text = "If you leave you will lose game progress.",
            confirmButtonText = "Stay",
            dismissButtonText = "Exit"
        )
    }

    BackHandler(
        onBack = {
            updateExitGameDialogState(true)
        }
    )
    GameScreenContent(
        players = players,
        showCalcDialog = {
            calcDialogState.value = true
        }
    )

    if (calcDialogState.value) {
        CalcDialog(
            closeCalcDialog = {
                calcDialogState.value = false
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreenContent(
    players: List<Player>,
    showCalcDialog: () -> Unit
) {
    Scaffold(
        modifier = Modifier.testTag(GameScreenTestTags.GAME_SCREEN),
        content = {
            Column(
                modifier = Modifier.padding(all = Dimen.padding_standard)
            ) {
                Text(
                    modifier = Modifier.testTag(GameScreenTestTags.WILD_CARD),
                    text = FiveCrownsConstants.INITIAL_WILD_CARD.toString()
                )

                LazyColumn(
                    modifier = Modifier
                        .testTag(GameScreenTestTags.PLAYER_COLUMN)
                        .fillMaxWidth()
                        .weight(1f),
                    content = {
                        itemsIndexed(players) { _, player ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                content = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp, horizontal = 8.dp)
                                            .background(Color.Blue),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier.testTag(GameScreenTestTags.PLAYER_NAME),
                                            text = player.name
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            modifier = Modifier.testTag(GameScreenTestTags.PLAYER_SCORE),
                                            text = player.score.toString(),
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))

                                        Button(
                                            modifier = Modifier.testTag(GameScreenTestTags.CALC_BUTTON),
                                            onClick = showCalcDialog
                                        ) {
                                            Text(text = 0.toString())
                                        }
                                    }
                                }
                            )
                        }
                    }
                )

                Button(
                    modifier = Modifier
                        .testTag(GameScreenTestTags.TALLY_BUTTON)
                        .fillMaxWidth(),
                    onClick = showCalcDialog
                ) {
                    Text(
                        text = "Tally"
                    )
                }
            }
        }
    )
}

object GameScreenTestTags {
    const val GAME_SCREEN = "GAME_SCREEN"
    const val PLAYER_COLUMN = "GAME_PLAYER_COLUMN"
    const val WILD_CARD = "WILD_CARD"
    const val TALLY_BUTTON = "TALLY_BUTTON"
    const val PLAYER_NAME = "PLAYER_CARD_PLAYER_NAME"
    const val PLAYER_SCORE = "PLAYER_CARD_PLAYER_SCORE"
    const val CALC_BUTTON = "GAME_SCREEN_CALC_BUTTON"
}