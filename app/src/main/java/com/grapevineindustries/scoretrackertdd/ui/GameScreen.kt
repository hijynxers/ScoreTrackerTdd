package com.grapevineindustries.scoretrackertdd.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.viewmodel.Player

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(
    onBackPressed: () -> Unit,
    players: SnapshotStateList<Player>,
    backDialogState: Boolean,
    updateDialogState: (Boolean) -> Unit
) {
    if (backDialogState) {
        AlertDialog(
            modifier = Modifier.testTag(""),
            title = {  },
            text = {  },
            confirmButton = {
                Button(onClick = {
                    updateDialogState(false)
                }) {
                    Text(text = "ok")
                }
            },
            onDismissRequest = { /*TODO*/ }
        )
    }

    BackHandler(
        onBack = {
            updateDialogState(true)
        }
    )

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
                        .weight(1f),
                    content = {
                        itemsIndexed(players) { index, player ->
                            Text(text = "nathan")


                        }
                    }
                )
                
                Button(
                    modifier = Modifier.testTag(GameScreenTestTags.TALLY_BUTTON),
                    onClick = { /*TODO*/ }
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
}