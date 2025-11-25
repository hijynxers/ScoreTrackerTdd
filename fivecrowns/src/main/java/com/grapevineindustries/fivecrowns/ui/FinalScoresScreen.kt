package com.grapevineindustries.fivecrowns.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.common.R
import com.grapevineindustries.common.STButton
import com.grapevineindustries.common.theme.Dimen
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.Player

@Composable
fun FinalScoreScreen(
    playerData: List<Player>,
    onNewGameClick: () -> Unit,
    onReplayClick: () -> Unit
) {
    BackHandler(
        onBack = { }
    )

    Column(
        modifier = Modifier.padding(horizontal = Dimen.outerScreenPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            content = {
                items(playerData) { player ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 45.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.testTag(FinalScoresScreenTestTags.PLAYER_NAME),
                            text = player.name
                        )

                        Text(
                            modifier = Modifier.testTag(FinalScoresScreenTestTags.PLAYER_SCORE),
                            text = player.score.toString()
                        )
                    }
                }
            }
        )

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
            STButton(
                onClick = onNewGameClick,
                text = stringResource(id = R.string.new_game),
                testTag = FinalScoresScreenTestTags.NEW_GAME
            )

            Spacer(modifier = Modifier.width(16.dp))

            STButton(
                onClick = onReplayClick,
                text = stringResource(id = R.string.replay),
                testTag = FinalScoresScreenTestTags.REPLAY,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun FinalScoreScreenPreview() {
    ScoreTrackerTheme {
        FinalScoreScreen(
            playerData = listOf(
                Player(name = "Player 1", score = 100),
                Player(name = "Player 2", score = 50),
                Player(name = "Player 3", score = 120),
                Player(name = "Player 4", score = 10)
            ),
            onNewGameClick = {},
            onReplayClick = {}
        )
    }
}

object FinalScoresScreenTestTags {
    const val PLAYER_NAME = "FINAL_SCORES_PLAYER_NAME"
    const val PLAYER_SCORE = "FINAL_SCORES_PLAYER_SCORE"
    const val REPLAY = "FINAL_SCORES_REPLAY"
    const val NEW_GAME = "FINAL_SCORES_NEW_GAME"
}