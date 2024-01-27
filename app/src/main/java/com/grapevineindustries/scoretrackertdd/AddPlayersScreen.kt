package com.grapevineindustries.scoretrackertdd

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.grapevineindustries.scoretrackertdd.theme.Dimen

@Preview
@Composable
fun AddPlayerPreview() {
//    ScoreTrackerTheme {
//        AddPlayersScreen(
//            players = arrayListOf(
//                Player("name1"),
//                Player("name2"),
//                Player("name3")
//            ),
//            updatePlayerName = ,
//            onStatGameClicked = {}
//        )
//    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPlayersScreen(
    players: SnapshotStateList<Player>,
    updatePlayerName: (Int, String) -> Unit,
    onStatGameClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.testTag(AddPlayersScreenTestTags.ADD_PLAYERS_SCREEN),
        content = {
            Column(
                modifier = Modifier.padding(all = Dimen.padding_standard)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .testTag(AddPlayersScreenTestTags.PLAYER_COLUMN)
                        .weight(1f)
                ) {
                    itemsIndexed(players) { index, player ->
                        OutlinedTextField(
                            modifier = Modifier.testTag(AddPlayersScreenTestTags.PLAYER_TEXT_INPUT + index),
                            value = players[index].name,
                            onValueChange = { it ->
                                it.also {
                                    updatePlayerName(index, it)
                                }
                            },
                            singleLine = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.Sentences,
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .testTag(AddPlayersScreenTestTags.START_GAME_BUTTON)
                        .fillMaxWidth(),
                    onClick = {

                        onStatGameClicked()
                    }
                ) {
                    Text(
                        text = "Start Game"
                    )
                }
            }
        }
    )
}

object AddPlayersScreenTestTags {
    const val ADD_PLAYERS_SCREEN = "ADD_PLAYERS_SCREEN"
    const val PLAYER_TEXT_INPUT = "PLAYER_NAME_INPUT"
    const val PLAYER_COLUMN = "PLAYER_COLUMN"
    const val START_GAME_BUTTON = "START_GAME_BUTTON"
}