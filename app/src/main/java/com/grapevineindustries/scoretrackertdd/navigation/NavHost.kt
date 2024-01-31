package com.grapevineindustries.scoretrackertdd.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.viewmodel.PlayersViewModel
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.GameScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "landingScreen",
    viewModel: PlayersViewModel = PlayersViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("landingScreen") {
            LandingScreen(
                onAddPlayersClick = { numPlayers ->
                    viewModel.createPlayersList(numPlayers)
                    navController.navigate("addPlayersScreen")
                }
            )
        }
        composable("addPlayersScreen") {
            AddPlayersScreen(
                updatePlayerName = viewModel::setName,
                onStatGameClicked = {
                    navController.navigate("gameScreen")
                },
                players = viewModel.playerList,
            )
        }
        composable("gameScreen") {
            val gameModel = remember { GameViewModel() }

            GameScreen(
                onCloseGame = {
                    viewModel.reset()
                    gameModel.reset()
                    navController.popBackStack(route = "landingScreen", inclusive = false)
                },
                players = viewModel.playerList,
                exitGameDialogState = gameModel.exitGameDialogState.collectAsState().value,
                updateExitGameDialogState = gameModel::updateExitGameDialogState
            )
        }
    }
}