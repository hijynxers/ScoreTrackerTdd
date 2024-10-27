package com.grapevineindustries.scoretrackertdd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayerViewModel

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavHostRoutes.LandingScreen,
    playerViewModel: PlayerViewModel = PlayerViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavHostRoutes.LandingScreen) {
            LandingScreen(
                onAddPlayersClick = { numPlayers ->
                    playerViewModel.createPlayersList(numPlayers)
                    navController.navigate(NavHostRoutes.AddPlayersScreen)
                }
            )
        }
        composable(NavHostRoutes.AddPlayersScreen) {
            AddPlayersScreen(
                onStartGameClicked = {
                    navController.navigate(NavHostRoutes.GameScreen)
                },
                onBackPress = {
                    navController.navigateUp()
                },
                updatePlayer = playerViewModel::updatePlayer,
                players = playerViewModel.players
            )
        }
        composable(NavHostRoutes.GameScreen) {
            FiveCrownsScreen(
                playerViewModel = playerViewModel,
                navigateToLandingScreen = {
                    navController.popBackStack(
                        route = NavHostRoutes.LandingScreen,
                        inclusive = false
                    )
                },
                navigateToFinalScoreScreen = {
                    navController.navigate(NavHostRoutes.FinalScoresScreen)
                }
            )
        }
        composable(NavHostRoutes.FinalScoresScreen) {
            FinalScoreScreen(
                playerData = playerViewModel.sortedPlayers(),
                onNewGameClick = {
                    navController.popBackStack(
                        route = NavHostRoutes.LandingScreen,
                        inclusive = false
                    )
                },
                onReplayClick = {
                    playerViewModel.resetScores()
                    navController.popBackStack(
                        route = NavHostRoutes.GameScreen,
                        inclusive = false
                    )
                }
            )
        }
    }
}

object NavHostRoutes {
    const val LandingScreen = "NAV_LANDING_SCREEN"
    const val AddPlayersScreen = "NAV_ADD_PLAYER_SCREEN"
    const val GameScreen = "NAV_GAME_SCREEN"
    const val FinalScoresScreen = "NAV_FINAL_SCORES_SCREEN"
}