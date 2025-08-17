package com.grapevineindustries.scoretrackertdd.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.grapevineindustries.scoretrackertdd.resetGameAndPlayers
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel

fun NavGraphBuilder.fiveCrownsGraph(
    navController: NavHostController,
    gameViewModel: GameViewModel
) {
    composable(FiveCrownsDestinations.LANDING) {
        // You might want to pass a specific NavController for this graph if its
        // backstack behavior is completely isolated. For now, using the main one.
        LandingScreen( // Your existing Five Crowns LandingScreen
            onAddPlayersClick = { numPlayers ->
                gameViewModel.createPlayersList(numPlayers)
                navController.navigate(FiveCrownsDestinations.ADD_PLAYERS)
                // Potentially signal to hide Main TopAppBar from here
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
            navigateToLandingScreen = { // This should navigate to the landing of *this graph*
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
                gameViewModel.resetGameAndPlayers() // Assuming a method to reset
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
}

// --- Define Routes for within Five Crowns (example) ---
object FiveCrownsDestinations {
    const val LANDING = "fiveCrownsLanding" // Renamed to avoid clash if you have a global landing
    const val ADD_PLAYERS = "fiveCrownsAddPlayers"
    const val GAME = "fiveCrownsGame"
    const val FINAL_SCORES = "fiveCrownsFinalScores"
}