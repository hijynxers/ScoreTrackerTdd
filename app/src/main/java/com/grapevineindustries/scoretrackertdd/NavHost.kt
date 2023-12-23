package com.grapevineindustries.scoretrackertdd

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "landingScreen"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("landingScreen") {
            LandingScreen(
                onAddPlayersClick = { numPlayers ->
                    navController.navigate("addPlayersScreen/${numPlayers}")
                }
            )
        }
        composable("addPlayersScreen/{numPlayers}") { navBackStackEntry ->
            val numPlayers = navBackStackEntry.arguments?.getString("numPlayers")
            numPlayers?.let {
                AddPlayersScreen(numPlayers = it.toInt())
            }

        }
    }
}