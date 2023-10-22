package com.grapevineindustries.scoretrackertdd

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.theme.padding_standard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    ScoreTrackerTheme {
        Scaffold(
            topBar = {},
            content = {
                val numPlayers = remember { mutableIntStateOf(FiveCrownsConstants.DEFAULT_NUM_PLAYERS)  }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(all = padding_standard),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(2f))

                    Text(
                        modifier = Modifier.testTag(MainScreenTestTags.TITLE),
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
                            modifier = Modifier.testTag(MainScreenTestTags.NUM_PLAYERS_MINUS),
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
                            modifier = Modifier.testTag(MainScreenTestTags.NUM_PLAYERS),
                            text = numPlayers.intValue.toString()
                        )

                        TextButton(
                            modifier = Modifier.testTag(MainScreenTestTags.NUM_PLAYERS_ADD),
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
                        modifier = Modifier.testTag(MainScreenTestTags.ADD_PLAYERS_BUTTON)
                            .fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        content = {
                            Text(
                                text = "Start Game",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        )
    }
}

object MainScreenTestTags {
    const val TITLE = "TITLE"
    const val NUM_PLAYERS = "NUM_PLAYERS"
    const val NUM_PLAYERS_ADD = "NUM_PLAYERS_ADD"
    const val NUM_PLAYERS_MINUS = "NUM_PLAYERS_MINUS"
    const val ADD_PLAYERS_BUTTON = "ADD_PLAYERS_BUTTON"
}