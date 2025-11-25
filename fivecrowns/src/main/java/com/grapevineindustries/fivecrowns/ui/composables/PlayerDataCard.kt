package com.grapevineindustries.fivecrowns.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.common.theme.ScoreTrackerTheme
import com.grapevineindustries.fivecrowns.Player
import com.grapevineindustries.fivecrowns.ui.FiveCrownsScreenTestTags

@Preview(showBackground = true)
@Composable
fun PlayerDataCardPreviewLight() {
    ScoreTrackerTheme {
        Column {
            PlayerDataCard(player = Player("Nathan"), onClick = {  }, isDealer = true)
            PlayerDataCard(player = Player("Nathan"), onClick = {  }, isDealer = false)
            PlayerDataCard(player = Player("Nathan"), onClick = {  }, isDealer = false)
        }
    }
}
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PlayerDataCardPreviewDark() {
    ScoreTrackerTheme {
        Column {
            PlayerDataCard(player = Player("Nathan"), onClick = {  }, isDealer = true)
            PlayerDataCard(player = Player("Nathan"), onClick = {  }, isDealer = false)
            PlayerDataCard(player = Player("Nathan"), onClick = {  }, isDealer = false)
        }
    }
}

@Composable
fun PlayerDataCard(
    player: Player,
    onClick: () -> Unit,
    isDealer: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDealer) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.testTag(FiveCrownsScreenTestTags.PLAYER_NAME),
                    text = player.name
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.testTag(FiveCrownsScreenTestTags.PLAYER_SCORE),
                    text = player.score.toString(),
                )
                Spacer(modifier = Modifier.width(8.dp))


                Button(
                    modifier = Modifier
                        .testTag(FiveCrownsScreenTestTags.CALC_BUTTON),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDealer) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    ),
                    onClick = onClick
                ) {
                    Text(
                        modifier = Modifier.testTag(FiveCrownsScreenTestTags.PLAYER_POTENTIAL_POINTS),
                        text = player.pendingPoints.toString(),
                        color = if (isDealer) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                }
            }
        }
    )
}