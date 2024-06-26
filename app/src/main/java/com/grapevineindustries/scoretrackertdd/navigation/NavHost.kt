package com.grapevineindustries.scoretrackertdd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.FiveCrownsViewModel

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavHostRoutesEnum.LandingScreen.name,
    scoreTrackerViewModel: ScoreTrackerViewModel = ScoreTrackerViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavHostRoutesEnum.LandingScreen.name) {
            LandingScreen(
                updateGame = scoreTrackerViewModel::updateGame,
                game = scoreTrackerViewModel.game.collectAsState().value,
                onAddPlayersClick = { numPlayers ->
                    scoreTrackerViewModel.createPlayersList(numPlayers)
                    navController.navigate(
                        NavHostRoutesEnum.AddPlayersScreen.name
                    )
                }
            )
        }
        composable(NavHostRoutesEnum.AddPlayersScreen.name) {
            AddPlayersScreen(
                updatePlayerName = scoreTrackerViewModel::setName,
                onStatGameClicked = {
                    navController.navigate(NavHostRoutesEnum.GameScreen.name)
                },
                onBackPress = {
                    scoreTrackerViewModel.reset()
                    navController.navigateUp()
                },
                players = scoreTrackerViewModel.playerList,
            )
        }
        composable(NavHostRoutesEnum.GameScreen.name) {
            val fiveCrownsViewModel = remember { FiveCrownsViewModel() }

            FiveCrownsScreen(
                onCloseGame = {
                    scoreTrackerViewModel.reset()
                    fiveCrownsViewModel.reset()
                    navController.popBackStack(route = NavHostRoutesEnum.LandingScreen.name, inclusive = false)
                },
                players = scoreTrackerViewModel.playerList,
                exitGameDialogState = fiveCrownsViewModel.exitGameDialogState.collectAsState().value,
                updateExitGameDialogState = fiveCrownsViewModel::updateExitGameDialogState,
                updatePotentialPoints = scoreTrackerViewModel::setPotentialPoints,
                tallyPoints = {
                    scoreTrackerViewModel.tallyPoints()
                    if (fiveCrownsViewModel.endgameCondition()) {
                        navController.navigate(NavHostRoutesEnum.FinalScoresScreen.name)
                    } else {
                        fiveCrownsViewModel.incrementWildCard()
                        scoreTrackerViewModel.incrementDealer()
                    }
                },
                wildCard = fiveCrownsViewModel.wildCard.collectAsState().value,
                dealerIndex = scoreTrackerViewModel.dealer.collectAsState().value
            )
        }
        composable(NavHostRoutesEnum.FinalScoresScreen.name) {
            FinalScoreScreen(
                playerData = scoreTrackerViewModel.sortedPlayers(),
                onNewGameClick = {
                    scoreTrackerViewModel.reset()
                    navController.popBackStack(route = NavHostRoutesEnum.LandingScreen.name, inclusive = false)
                },
                onReplayClick = {
                    scoreTrackerViewModel.resetScores()
                    navController.popBackStack(
                        route = NavHostRoutesEnum.GameScreen.name,
                        inclusive = false
                    )
                }
            )
        }
    }
}

enum class NavHostRoutesEnum {
    LandingScreen,
    AddPlayersScreen,
    GameScreen,
    FinalScoresScreen
}