package com.grapevineindustries.scoretrackertdd.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.viewmodel.Player

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPlayersScreen(
    onStartGameClicked: () -> Unit,
    onBackPress: () -> Unit,
    updatePlayer: (Int, Player) -> Unit,
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
                modifier = Modifier.padding(all = Dimen.outerScreenPadding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .testTag(AddPlayersScreenTestTags.PLAYER_COLUMN)
                        .weight(1f)
                ) {
                    itemsIndexed(players) { index, player ->
                        OutlinedTextField(
                            modifier = Modifier.testTag(AddPlayersScreenTestTags.PLAYER_TEXT_INPUT + index),
                            value = player.name,
                            onValueChange = { it ->
                                it.also {
                                    updatePlayer(
                                        index,
                                        player.copy(
                                            name = it
                                        )
                                    )
                                }
                            },
                            singleLine = true,
                            maxLines = 1
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .testTag(AddPlayersScreenTestTags.START_GAME_BUTTON)
                        .fillMaxWidth(),
                    onClick = {
//                        onStartGameClicked(viewModel.playerList)
                        onStartGameClicked()
                    }
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