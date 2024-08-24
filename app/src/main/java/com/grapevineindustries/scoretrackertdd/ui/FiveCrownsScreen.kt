package com.grapevineindustries.scoretrackertdd.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.FiveCrownsCalcDialog
import com.grapevineindustries.scoretrackertdd.ui.composables.PlayerDataCard
import com.grapevineindustries.scoretrackertdd.ui.composables.ScoreTrackerAlertDialog
import com.grapevineindustries.scoretrackertdd.ui.composables.convertWildCard
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsViewModel
import com.grapevineindustries.scoretrackertdd.viewmodel.Player

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FiveCrownsScreenPreview() {
    val lastClickedIndex = remember { mutableIntStateOf(-1) }

    ScoreTrackerTheme {
        FiveCrownsScreenContent(
            players = listOf(
                Player("player 1", 0),
                Player("player 2", 12),
                Player("player 3", 145),
            ),
            showCalcDialog = {},
            tallyPoints = {},
            lastClickedIndex = lastClickedIndex,
            wildCard = 3,
            dealerIndex = 0
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FiveCrownsScreen(
    onCloseGame: () -> Unit,
    onEndGameClick: () -> Unit,
    tallyPoints: () -> Unit,
    updatePotentialPoints: (Int, Int) -> Unit,
    players: SnapshotStateList<Player>
) {
    val calcDialogState = remember { mutableStateOf(false) }
    val lastClickedIndex = remember { mutableIntStateOf(-1) }
    val fiveCrownsViewModel = remember { FiveCrownsViewModel() }

    if (fiveCrownsViewModel.exitGameDialogState.collectAsState().value) {
        ScoreTrackerAlertDialog(
            onConfirmClick = { fiveCrownsViewModel.updateExitGameDialogState(false) },
            onDismissClick = {
                fiveCrownsViewModel.reset()
                onCloseGame()
            },
            title = stringResource(R.string.leave_game),
            text = stringResource(R.string.lose_game_progress),
            confirmButtonText = stringResource(R.string.stay),
            dismissButtonText = stringResource(R.string.exit)
        )
    }

    BackHandler(
        onBack = {
            fiveCrownsViewModel.updateExitGameDialogState(true)
        }
    )

    FiveCrownsScreenContent(
        players = players,
        showCalcDialog = { calcDialogState.value = true },
        tallyPoints = {
            tallyPoints()
            if (fiveCrownsViewModel.endgameCondition()) {
                onEndGameClick()
            } else {
                fiveCrownsViewModel.incrementWildCard()
                fiveCrownsViewModel.incrementDealer()
            }
        },
        lastClickedIndex = lastClickedIndex,
        wildCard = fiveCrownsViewModel.wildCard.collectAsState().value,
        dealerIndex = fiveCrownsViewModel.dealer.collectAsState().value
    )

    if (calcDialogState.value) {
        FiveCrownsCalcDialog(
            closeWithPoints = { points ->
                updatePotentialPoints(lastClickedIndex.intValue, points)
                calcDialogState.value = false
            },
            cancelDialog = { calcDialogState.value = false },
            wildCard = fiveCrownsViewModel.wildCard.collectAsState().value
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FiveCrownsScreenContent(
    showCalcDialog: () -> Unit,
    tallyPoints: () -> Unit,
    players: List<Player>,
    lastClickedIndex: MutableIntState,
    wildCard: Int,
    dealerIndex: Int
) {
    Scaffold(
        modifier = Modifier.testTag(FiveCrownsScreenTestTags.SCREEN),
        content = {
            Column(
                modifier = Modifier.padding(all = Dimen.padding_standard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(id = R.string.wildcard),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.testTag(FiveCrownsScreenTestTags.WILD_CARD),
                        text = convertWildCard(wildCard),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }


                LazyColumn(
                    modifier = Modifier
                        .testTag(FiveCrownsScreenTestTags.PLAYER_COLUMN)
                        .fillMaxWidth()
                        .weight(1f),
                    content = {
                        itemsIndexed(players) { index, player ->
                            PlayerDataCard(
                                player = player,
                                onClick = {
                                    lastClickedIndex.intValue = index
                                    showCalcDialog()
                                },
                                isDealer = (dealerIndex % players.size == index)
                            )
                        }
                    }
                )

                Button(
                    modifier = Modifier
                        .testTag(FiveCrownsScreenTestTags.TALLY_BUTTON)
                        .fillMaxWidth(),
                    onClick = tallyPoints
                ) {
                    Text(
                        text = stringResource(id = R.string.tally)
                    )
                }
            }
        }
    )
}

object FiveCrownsScreenTestTags {
    const val SCREEN = "FIVE_CROWNS_SCREEN"
    const val PLAYER_COLUMN = "FIVE_CROWNS_PLAYER_COLUMN"
    const val WILD_CARD = "FIVE_CROWNS_WILD_CARD"
    const val TALLY_BUTTON = "FIVE_CROWNS_TALLY_BUTTON"
    const val PLAYER_NAME = "FIVE_CROWNS_PLAYER_CARD_PLAYER_NAME"
    const val PLAYER_SCORE = "FIVE_CROWNS_PLAYER_CARD_PLAYER_SCORE"
    const val CALC_BUTTON = "-FIVE_CROWNS_SCREEN_CALC_BUTTON"
}