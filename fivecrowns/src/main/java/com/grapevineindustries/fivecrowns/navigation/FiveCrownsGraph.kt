package com.grapevineindustries.fivecrowns.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.grapevineindustries.fivecrowns.ui.FinalScoreScreen
import com.grapevineindustries.fivecrowns.ui.AddPlayersScreen
import com.grapevineindustries.fivecrowns.ui.FiveCrownsScreen
import com.grapevineindustries.fivecrowns.ui.HighLowScoreScreen
import com.grapevineindustries.fivecrowns.ui.LandingScreen
import com.grapevineindustries.fivecrowns.viewmodel.GameViewModel

fun NavGraphBuilder.fiveCrownsGraph(
    navController: NavHostController,
    gameViewModel: GameViewModel
) {
    composable(FiveCrownsDestinations.LANDING) {
        LandingScreen(
            onAddPlayersClick = { numPlayers ->
                gameViewModel.createPlayersList(numPlayers)
                navController.navigate(FiveCrownsDestinations.ADD_PLAYERS)
            },
            onNavigateToHighLowScores = {
                navController.navigate(FiveCrownsDestinations.HIGH_LOW_SCORES)
            }
        )
    }
    composable(FiveCrownsDestinations.ADD_PLAYERS) {
        AddPlayersScreen(
            onStartGameClicked = {
                navController.navigate(FiveCrownsDestinations.GAME)
            },
            onBackPress = { navController.popBackStack() },
            updatePlayer = gameViewModel::updatePlayer,
            players = gameViewModel.players
        )
    }
    composable(FiveCrownsDestinations.GAME) {
        FiveCrownsScreen(
            gameViewModel = gameViewModel,
            navigateToLandingScreen = {
                navController.popBackStack(
                    route = FiveCrownsDestinations.LANDING,
                    inclusive = false
                )
            },
            navigateToFinalScoreScreen = {
                navController.navigate(FiveCrownsDestinations.FINAL_SCORES)
            }
        )
    }
    composable(FiveCrownsDestinations.FINAL_SCORES) {
        FinalScoreScreen(
            playerData = gameViewModel.sortedPlayers(),
            onNewGameClick = {
                gameViewModel.resetGameAndPlayers()
                navController.popBackStack(
                    route = FiveCrownsDestinations.LANDING,
                    inclusive = false
                )
            },
            onReplayClick = {
                gameViewModel.resetScores()
                gameViewModel.resetWildCard()
                navController.popBackStack(
                    route = FiveCrownsDestinations.GAME,
                    inclusive = false
                )
            }
        )
    }
    composable(FiveCrownsDestinations.HIGH_LOW_SCORES) {
        HighLowScoreScreen()
    }
}

object FiveCrownsDestinations {
    const val LANDING = "fiveCrownsLanding"
    const val ADD_PLAYERS = "fiveCrownsAddPlayers"
    const val GAME = "fiveCrownsGame"
    const val FINAL_SCORES = "fiveCrownsFinalScores"
    const val HIGH_LOW_SCORES = "highLowScores"
}