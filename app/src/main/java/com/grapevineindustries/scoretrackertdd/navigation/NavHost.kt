package com.grapevineindustries.scoretrackertdd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel

@Composable
fun FiveCrownsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    showNavBar: (Boolean) -> Unit,
    startDestination: String = FiveCrownsNavHostRoutes.LandingScreen,
    gameViewModel: GameViewModel = viewModel(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
//    navigation(
//        startDestination = FiveCrownsNavHostRoutes.LandingScreen,
//    ) {
        composable(FiveCrownsNavHostRoutes.LandingScreen) {
            LandingScreen(
                onAddPlayersClick = { numPlayers ->
                    gameViewModel.createPlayersList(numPlayers)
                    navController.navigate(FiveCrownsNavHostRoutes.AddPlayersScreen)
                    showNavBar(false)
                }
            )
        }
        composable(FiveCrownsNavHostRoutes.AddPlayersScreen) {
            AddPlayersScreen(
                onStartGameClicked = {
                    navController.navigate(FiveCrownsNavHostRoutes.GameScreen)
                },
                onBackPress = {
                    navController.navigateUp()
                    showNavBar(true)
                },
                updatePlayer = gameViewModel::updatePlayer,
                players = gameViewModel.players
            )
        }
        composable(FiveCrownsNavHostRoutes.GameScreen) {
            FiveCrownsScreen(
                gameViewModel = gameViewModel,
                navigateToLandingScreen = {
                    navController.popBackStack(
                        route = FiveCrownsNavHostRoutes.LandingScreen,
                        inclusive = false
                    )
                },
                navigateToFinalScoreScreen = {
                    navController.navigate(FiveCrownsNavHostRoutes.FinalScoresScreen)
                }
            )
        }
        composable(FiveCrownsNavHostRoutes.FinalScoresScreen) {
            FinalScoreScreen(
                playerData = gameViewModel.sortedPlayers(),
                onNewGameClick = {
                    navController.popBackStack(
                        route = FiveCrownsNavHostRoutes.LandingScreen,
                        inclusive = false
                    )
                    gameViewModel.resetWildCard()
                    showNavBar(true)
                },
                onReplayClick = {
                    gameViewModel.resetScores()
                    gameViewModel.resetWildCard()
                    navController.popBackStack(
                        route = FiveCrownsNavHostRoutes.GameScreen,
                        inclusive = false
                    )
                }
            )
        }
    }
}

object FiveCrownsNavHostRoutes {
    const val LandingScreen = "NAV_LANDING_SCREEN"
    const val AddPlayersScreen = "NAV_ADD_PLAYER_SCREEN"
    const val GameScreen = "NAV_GAME_SCREEN"
    const val FinalScoresScreen = "NAV_FINAL_SCORES_SCREEN"
}