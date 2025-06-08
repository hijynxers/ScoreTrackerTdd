package com.grapevineindustries.scoretrackertdd.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grapevineindustries.scoretrackertdd.Player
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.composables.FiveCrownsCalcDialog
import com.grapevineindustries.scoretrackertdd.ui.composables.PlayerDataCard
import com.grapevineindustries.scoretrackertdd.ui.composables.STButton
import com.grapevineindustries.scoretrackertdd.ui.composables.ScoreTrackerAlertDialog
import com.grapevineindustries.scoretrackertdd.ui.composables.convertWildCard
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsState
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel

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
            state = FiveCrownsState()
        )
    }
}

@Composable
fun FiveCrownsScreen(
    gameViewModel: GameViewModel = viewModel(),
    navigateToLandingScreen: () -> Unit,
    navigateToFinalScoreScreen: (List<Player>) -> Unit
) {
    val lastClickedIndex = remember { mutableIntStateOf(-1) }

    BackHandler(
        onBack = {
            gameViewModel.updateExitGameDialogState(true)
        }
    )

    if (gameViewModel.state.collectAsState().value.isExitGameDialogShowing) {
        ScoreTrackerAlertDialog(
            onConfirmClick = { gameViewModel.updateExitGameDialogState(false) },
            onDismissClick = {
                gameViewModel.updateExitGameDialogState(false)
                navigateToLandingScreen()
            },
            title = stringResource(R.string.leave_game),
            text = stringResource(R.string.lose_game_progress),
            confirmButtonText = stringResource(R.string.stay),
            dismissButtonText = stringResource(R.string.exit)
        )
    }

    FiveCrownsScreenContent(
        players = gameViewModel.players,
        showCalcDialog = {
            gameViewModel.updateCalcDialogState(true)
        },
        tallyPoints = {
            gameViewModel.tallyPoints()
            if (!gameViewModel.state.value.isNoScoreDialogShowing) {
                if (gameViewModel.endgameCondition()) {
                    navigateToFinalScoreScreen(gameViewModel.players)
                } else {
                    gameViewModel.incrementDealer()
                    gameViewModel.incrementWildCard()
                }
            }
        },
        lastClickedIndex = lastClickedIndex,
        state = gameViewModel.state.collectAsState().value
    )

    if (gameViewModel.state.collectAsState().value.isNoScoreDialogShowing) {
        ScoreTrackerAlertDialog(
            title = "Tally scores?",
            text = "No points were entered. Are you sure you want to tally?",
            confirmButtonText = "Yes",
            onConfirmClick = {
                gameViewModel.updateNoScoreDialogState(false)
                if (gameViewModel.endgameCondition()) {
                    navigateToFinalScoreScreen(gameViewModel.players)
                } else {
                    gameViewModel.incrementDealer()
                    gameViewModel.incrementWildCard()
                }
            },
            dismissButtonText = "No",
            onDismissClick = { gameViewModel.updateNoScoreDialogState(false) }
        )
    }

    if (gameViewModel.state.collectAsState().value.isCalcDialogShowing) {
        FiveCrownsCalcDialog(
            closeWithPoints = { points ->
                gameViewModel.setPotentialPoints(lastClickedIndex.intValue, points)
                gameViewModel.updateCalcDialogState(false)
            },
            cancelDialog = { gameViewModel.updateCalcDialogState(false) },
            wildCard = gameViewModel.state.collectAsState().value.wildCard,
            player = gameViewModel.players[lastClickedIndex.intValue].name
        )
    }
}

@Composable
fun FiveCrownsScreenContent(
    players: List<Player>,
    showCalcDialog: () -> Unit,
    tallyPoints: () -> Unit,
    lastClickedIndex: MutableIntState,
    state: FiveCrownsState
) {
    Scaffold(
        modifier = Modifier.testTag(FiveCrownsScreenTestTags.SCREEN),
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(
                    start = Dimen.outerScreenPadding,
                    end = Dimen.outerScreenPadding,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
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
                        text = convertWildCard(state.wildCard),
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
                                isDealer = (state.dealer % players.size == index)
                            )
                        }
                    }
                )

                STButton(
                    onClick = tallyPoints,
                    text = stringResource(id = R.string.tally),
                    testTag = FiveCrownsScreenTestTags.TALLY_BUTTON,
                    modifier = Modifier.fillMaxWidth(),
                )
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
    const val PLAYER_POTENTIAL_POINTS = "FIVE_CROWNS_PLAYER_CARD_PLAYER_POTENTIAL_POINTS"
    const val CALC_BUTTON = "-FIVE_CROWNS_SCREEN_CALC_BUTTON"
}