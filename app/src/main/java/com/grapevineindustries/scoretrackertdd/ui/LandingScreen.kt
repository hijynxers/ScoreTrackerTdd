package com.grapevineindustries.scoretrackertdd.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LandingPreview() {
    LandingScreen(onAddPlayersClick = {})
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LandingScreen(
    onAddPlayersClick: (Int) -> Unit
) {
    ScoreTrackerTheme {
        Scaffold(
            topBar = {},
            content = {
                val numPlayers = remember { mutableIntStateOf(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)  }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(all = Dimen.padding_standard),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(2f))

                    Text(
                        modifier = Modifier.testTag(LandingScreenTestTags.GAME_TITLE),
                        text = stringResource(id = R.string.five_crowns),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(id = R.string.num_players),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            modifier = Modifier.testTag(LandingScreenTestTags.NUM_PLAYERS_MINUS),
                            onClick = {
                                if (numPlayers.intValue > FiveCrownsConstants.MIN_PLAYERS) {
                                    numPlayers.intValue = numPlayers.intValue - 1
                                }
                            },
                            content = {
                                Text(
                                    text = "-",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        )

                        Text(
                            modifier = Modifier.testTag(LandingScreenTestTags.NUM_PLAYERS),
                            text = numPlayers.intValue.toString()
                        )

                        TextButton(
                            modifier = Modifier.testTag(LandingScreenTestTags.NUM_PLAYERS_ADD),
                            onClick = {
                                if (numPlayers.intValue < FiveCrownsConstants.MAX_PLAYERS) {
                                    numPlayers.intValue = numPlayers.intValue + 1
                                }
                            },
                            content = {
                                Text(
                                    text = "+",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.weight(2f))

                    Button(
                        modifier = Modifier
                            .testTag(LandingScreenTestTags.ADD_PLAYERS_BUTTON)
                            .fillMaxWidth(),
                        onClick = { onAddPlayersClick(numPlayers.intValue) },
                        content = {
                            Text(
                                text = stringResource(id = R.string.start_game),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        )
    }
}

object LandingScreenTestTags {
    const val GAME_TITLE = "GAME_TITLE"
    const val NUM_PLAYERS = "NUM_PLAYERS"
    const val NUM_PLAYERS_ADD = "NUM_PLAYERS_ADD"
    const val NUM_PLAYERS_MINUS = "NUM_PLAYERS_MINUS"
    const val ADD_PLAYERS_BUTTON = "ADD_PLAYERS_BUTTON"
}