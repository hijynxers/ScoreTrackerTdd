package com.grapevineindustries.scoretrackertdd.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.viewmodel.Player

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPlayersScreen(
    updatePlayerName: (Int, String) -> Unit,
    onStatGameClicked: () -> Unit,
    onBackPress: () -> Unit,
    players: List<Player>
) {
    BackHandler(
        onBack = {
            onBackPress()
        }
    )

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
                    itemsIndexed(players) { index, _ ->
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
                    onClick = onStatGameClicked
                ) {
                    Text(
                        text = stringResource(id = R.string.start_game)
                    )
                }
            }
        }
    )
}

object AddPlayersScreenTestTags {
    const val ADD_PLAYERS_SCREEN = "ADD_PLAYERS_SCREEN"
    const val PLAYER_TEXT_INPUT = "PLAYER_NAME_INPUT"
    const val PLAYER_COLUMN = "ADD_PLAYER_PLAYER_COLUMN"
    const val START_GAME_BUTTON = "START_GAME_BUTTON"
}