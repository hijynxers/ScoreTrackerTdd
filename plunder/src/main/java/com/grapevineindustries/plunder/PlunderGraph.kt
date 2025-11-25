package com.grapevineindustries.plunder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.plunderGraph(navController: NavHostController) {
    composable(PlunderDestinations.PLUNDER) {
        PlunderScreen(
            onNavigateBackToHome = {
                navController.navigateUp()
            }
        )
    }
}

object PlunderDestinations {
    const val PLUNDER = "plunderScreen"
}