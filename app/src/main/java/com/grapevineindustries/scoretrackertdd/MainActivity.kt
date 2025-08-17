package com.grapevineindustries.scoretrackertdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.composables.crown
import com.composables.sailing
import com.grapevineindustries.scoretrackertdd.navigation.FiveCrownsDestinations
import com.grapevineindustries.scoretrackertdd.navigation.PlunderDestinations
import com.grapevineindustries.scoretrackertdd.navigation.fiveCrownsGraph
import com.grapevineindustries.scoretrackertdd.navigation.plunderGraph
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.viewmodel.GameViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppScreen()
        }
    }
}

// --- Define Routes for Drawer and Sections ---
object MainAppDestinations {
    const val FIVE_CROWNS_GRAPH = "fiveCrownsGraph"
    const val PLUNDER_GRAPH = "plunderGraph"
//    const val ABOUT_SCREEN = "aboutScreen"
}

data class DrawerMenuItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    ScoreTrackerTheme {
        val mainNavController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val currentRoute =
            mainNavController.currentBackStackEntryAsState().value?.destination?.route

        // Determine which TopAppBar to show or if to show one at all
        // This is a simplified example. You might need more complex logic.
        val showMainTopAppBar = currentRoute != FiveCrownsDestinations.ADD_PLAYERS &&
                currentRoute != FiveCrownsDestinations.GAME // etc.

        val drawerItems = listOf(
            DrawerMenuItem(
                MainAppDestinations.FIVE_CROWNS_GRAPH,
                "Five Crowns",
                crown
            ),
            DrawerMenuItem(MainAppDestinations.PLUNDER_GRAPH, "Plunder", sailing),
//            DrawerMenuItem(MainAppDestinations.ABOUT_SCREEN, "About", Icons.Filled.Info)
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
                    Column(modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)) {
                        Text(
                            "Score Tracker",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        HorizontalDivider()
                        drawerItems.forEach { item ->
                            NavigationDrawerItem(
                                icon = { Icon(item.icon, contentDescription = item.title) },
                                label = { Text(item.title) },
                                selected = currentRoute?.startsWith(item.route) == true,
                                onClick = {
                                    mainNavController.navigate(item.route) {
                                        popUpTo(mainNavController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    if (showMainTopAppBar) {
                        TopAppBar(
                            title = {
                                // Title could change based on the current section
                                val title = when {
                                    currentRoute?.startsWith(MainAppDestinations.FIVE_CROWNS_GRAPH) == true -> "Five Crowns"
                                    currentRoute?.startsWith(MainAppDestinations.PLUNDER_GRAPH) == true -> "Plunder"
//                                    currentRoute == MainAppDestinations.ABOUT_SCREEN -> "About"
                                    else -> "Score Tracker"
                                }
                                Text(title)
                            },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Filled.Menu, "Open Drawer")
                                }
                            }
                        )
                    }
                }
            ) { paddingValues ->
                MainAppNavHost(
                    mainNavController = mainNavController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun MainAppNavHost(mainNavController: NavHostController, modifier: Modifier = Modifier) {
    val gameViewModel: GameViewModel = viewModel()

    NavHost(
        navController = mainNavController,
        startDestination = MainAppDestinations.FIVE_CROWNS_GRAPH,
        modifier = modifier
    ) {
        navigation(
            route = MainAppDestinations.FIVE_CROWNS_GRAPH,
            startDestination = FiveCrownsDestinations.LANDING
        ) {
            fiveCrownsGraph(
                navController = mainNavController,
                gameViewModel = gameViewModel
            )
        }

        navigation(
            route = MainAppDestinations.PLUNDER_GRAPH,
            startDestination = PlunderDestinations.PLUNDER
        ) {
            plunderGraph(navController = mainNavController)
        }

//        composable(MainAppDestinations.ABOUT_SCREEN) {
//            AboutScreen(mainNavController)
//        }
    }
}

//@Composable
//fun AboutScreen(navController: NavHostController) {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text("About This App")
//    }
//}

// You'll need GameViewModel.resetGameAndPlayers() or similar
fun GameViewModel.resetGameAndPlayers() {
    // Implementation to clear players, scores, round, wildCard, etc.
    this.players.clear() // Example
    this.resetScores()
    this.resetWildCard()
}

