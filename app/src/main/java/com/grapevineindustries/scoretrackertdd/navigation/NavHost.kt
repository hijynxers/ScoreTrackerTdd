package com.grapevineindustries.scoretrackertdd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.ScoreTrackerViewModel

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
            FiveCrownsScreen(
                onCloseGame = {
                    scoreTrackerViewModel.reset()
                    navController.popBackStack(
                        route = NavHostRoutesEnum.LandingScreen.name,
                        inclusive = false
                    )
                },
                onEndGameClick = {
                    navController.navigate(NavHostRoutesEnum.FinalScoresScreen.name)
                },
                tallyPoints = scoreTrackerViewModel::tallyPoints,
                updatePotentialPoints = scoreTrackerViewModel::setPotentialPoints,
                players = scoreTrackerViewModel.playerList
            )
        }
        composable(NavHostRoutesEnum.FinalScoresScreen.name) {
            FinalScoreScreen(
                playerData = scoreTrackerViewModel.sortedPlayers(),
                onNewGameClick = {
                    scoreTrackerViewModel.reset()
                    navController.popBackStack(
                        route = NavHostRoutesEnum.LandingScreen.name,
                        inclusive = false
                    )
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