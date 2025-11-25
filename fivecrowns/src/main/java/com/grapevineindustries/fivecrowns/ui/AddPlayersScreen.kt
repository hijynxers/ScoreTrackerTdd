package com.grapevineindustries.fivecrowns.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.grapevineindustries.common.R
import com.grapevineindustries.common.STButton
import com.grapevineindustries.common.theme.Dimen
import com.grapevineindustries.fivecrowns.Player

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
    Column(
        modifier = Modifier.padding(horizontal = Dimen.outerScreenPadding)
    ) {
        Text("Enter Player Names:")
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier
                .testTag(AddPlayersScreenTestTags.PLAYER_COLUMN)
                .weight(1f)
        ) {
            itemsIndexed(players) { index, player ->
                OutlinedTextField(
                    modifier = Modifier.testTag(AddPlayersScreenTestTags.PLAYER_TEXT_INPUT + index),
                    label = {
                        Text("Player ${index + 1}")
                    },
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
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (index == players.size - 1) ImeAction.Done else ImeAction.Next
                    )
                )
            }
        }

        STButton(
            onClick = onStartGameClicked,
            text = stringResource(id = R.string.start_game),
            testTag = AddPlayersScreenTestTags.START_GAME_BUTTON,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

object AddPlayersScreenTestTags {
    const val PLAYER_TEXT_INPUT = "PLAYER_NAME_INPUT"
    const val PLAYER_COLUMN = "ADD_PLAYER_PLAYER_COLUMN"
    const val START_GAME_BUTTON = "START_GAME_BUTTON"
}