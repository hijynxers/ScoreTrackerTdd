package com.grapevineindustries.fivecrowns.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.grapevineindustries.common.R
import com.grapevineindustries.common.STButton
import com.grapevineindustries.common.theme.Dimen
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.FiveCrownsConstants.DEFAULT_NUM_PLAYERS
import com.grapevineindustries.fivecrowns.FiveCrownsConstants.MAX_PLAYERS
import com.grapevineindustries.fivecrowns.FiveCrownsConstants.MIN_PLAYERS

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LandingPreview() {
    LandingScreen(
        onAddPlayersClick = {}
    )
}

@Composable
fun LandingScreen(
    onAddPlayersClick: (Int) -> Unit
) {
    ScoreTrackerTheme {
        val numPlayers = rememberSaveable { mutableIntStateOf(DEFAULT_NUM_PLAYERS) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimen.outerScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))

            Text(
                modifier = Modifier.testTag(LandingScreenTestTags.GAME_TITLE),
                text = getGameTitle(GameOption.FIVE_CROWNS),
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
                        if (numPlayers.intValue > MIN_PLAYERS) {
                            numPlayers.intValue -= 1
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
                        if (numPlayers.intValue < MAX_PLAYERS) {
                            numPlayers.intValue += 1
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

            STButton(
                onClick = {
                    onAddPlayersClick(numPlayers.intValue)
                },
                text = stringResource(id = R.string.enter_players),
                testTag = LandingScreenTestTags.ADD_PLAYERS_BUTTON,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

fun getGameTitle(game: GameOption): String {
    return when (game) {
        GameOption.FIVE_CROWNS -> {
            "Five Crowns"
        }

        GameOption.RUMMY -> {
            "Rummy"
        }
    }
}

object LandingScreenTestTags {
    const val GAME_TITLE = "GAME_TITLE"
    const val NUM_PLAYERS = "NUM_PLAYERS"
    const val NUM_PLAYERS_ADD = "NUM_PLAYERS_ADD"
    const val NUM_PLAYERS_MINUS = "NUM_PLAYERS_MINUS"
    const val ADD_PLAYERS_BUTTON = "ADD_PLAYERS_BUTTON"
}

enum class GameOption {
    FIVE_CROWNS,
    RUMMY
}