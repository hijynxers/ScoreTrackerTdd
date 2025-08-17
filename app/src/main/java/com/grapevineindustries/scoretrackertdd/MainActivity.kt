package com.grapevineindustries.scoretrackertdd

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.grapevineindustries.scoretrackertdd.navigation.FiveCrownsNavHostRoutes
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.AddPlayersScreen
import com.grapevineindustries.scoretrackertdd.ui.FinalScoreScreen
import com.grapevineindustries.scoretrackertdd.ui.FiveCrownsScreen
import com.grapevineindustries.scoretrackertdd.ui.LandingScreen
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreen() {
    ScoreTrackerTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val showTopBar = rememberSaveable { mutableStateOf(true) }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    // Items for your navigation drawer
                    Text("Five Crowns", modifier = Modifier.padding(16.dp))
                    Text("Menu Item 2", modifier = Modifier.padding(16.dp))
                    // Add more items and navigation logic here
                    // e.g., navController.navigate("your_route")
                }
            }
        ) {
            Scaffold(
                topBar = {
                    if (showTopBar.value) {
                        TopAppBar(
                            title = { Text("Score Tracker") },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    },
                                    content = {
                                        Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                                    }
                                )
                            }
                        )
                    } else Unit
                }
            ) { paddingValues ->
                // Your NavHost will go here, using the paddingValues
//                NavHost(
//                    navController = navController,
//                    startDestination = NavHostRoutes.LandingScreen,
//                    modifier = Modifier.padding(paddingValues)
//                    // ... your existing NavHost setup
//                )
//                FiveCrownsNavHost(
//                    modifier = Modifier.padding(paddingValues),
//                    showNavBar = { showTopBar.value = it }
//                )

                AppNavHost(
                    navController = rememberNavController(),
                    modifier = Modifier.padding(paddingValues),
                    onScreenChange = { screen ->
                        showTopBar.value = screen != AppNavHostRoutes.LANDING_SCREEN
                    },
                    showNavBar = { showTopBar.value = it }
                )
            }
        }


    }
}



object AppNavHostRoutes {
    const val LANDING_SCREEN = "landingScreen" // Assuming you have a landing screen
    const val SCREEN_TWO = "screenTwo"
}
// Your NavHost composable
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onScreenChange: (String) -> Unit, // Callback to update selected drawer item,

    showNavBar: (Boolean) -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = FiveCrownsNavHostRoutes.LandingScreen, // Set your initial screen
        modifier = modifier
    ) {
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
        navigation(
            startDestination = "start", // Start destination for THIS nested graph
            route = "nathan"          // Route to identify this nested graph
        ) {
            composable("start") {
//                composable(FiveCrownsNavHostRoutes.LandingScreen) {
                    LandingScreen(
                        onAddPlayersClick = { numPlayers ->
//                            gameViewModel.createPlayersList(numPlayers)
//                            navController.navigate(FiveCrownsNavHostRoutes.AddPlayersScreen)
//                            showNavBar(false)
                        }
                    )
//                }
            }
//            composable(SettingsDestinations.NOTIFICATIONS) {
//                NotificationSettingsScreen(
//                    onNavigateBackToProfile = {
//                        navController.popBackStack() // Pops back to Profile within the settings graph
//                    }
//                )
//            }
        }
        composable(AppNavHostRoutes.SCREEN_TWO) {
            NotificationSettingsScreen(
                onNavigateBackToProfile = {
                    navController.popBackStack() // Pops back to Profile within the settings graph
                }
            )
            onScreenChange(AppNavHostRoutes.SCREEN_TWO)
        }
        // Add other destinations from your original NavHost here
        // e.g., your Five Crowns game screens
    }
}


//
//
//
//// Routes
//object MainDestinations {
//    const val HOME = "home"
//    const val SETTINGS_GRAPH = "settings_graph" // Route for the nested graph
//}
//
//object SettingsDestinations {
//    const val PROFILE = "profile"
//    const val NOTIFICATIONS = "notifications"
//}
//
//@Composable
//fun AppNavigationTest() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = MainDestinations.HOME) {
//        // Main App Destinations
//        composable(MainDestinations.HOME) {
//            HomeScreen(
//                onNavigateToSettings = {
//                    // Navigate to the START of the settings graph
//                    navController.navigate(MainDestinations.SETTINGS_GRAPH)
//                }
//            )
//        }
//
//        // Nested Settings Graph
//        navigation(
//            startDestination = SettingsDestinations.PROFILE, // Start destination for THIS nested graph
//            route = MainDestinations.SETTINGS_GRAPH          // Route to identify this nested graph
//        ) {
//            composable(SettingsDestinations.PROFILE) {
//                ProfileSettingsScreen(
//                    onNavigateToNotifications = {
//                        navController.navigate(SettingsDestinations.NOTIFICATIONS)
//                    },
//                    onNavigateBackToHome = { // Example of navigating out of the nested graph
//                        navController.popBackStack(MainDestinations.HOME, inclusive = false)
//                        // Or, if settings was launched on top of home:
//                        // navController.popBackStack() // This would go to home if settings was the last entry
//                    }
//                )
//            }
//            composable(SettingsDestinations.NOTIFICATIONS) {
//                NotificationSettingsScreen(
//                    onNavigateBackToProfile = {
//                        navController.popBackStack() // Pops back to Profile within the settings graph
//                    }
//                )
//            }
//        }
//        // You could have other main destinations or other nested graphs here
//    }
//}
//
//// Dummy Screen Composable
//@Composable
//fun HomeScreen(onNavigateToSettings: () -> Unit) {
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Home Screen")
//        Button(onClick = onNavigateToSettings) {
//            Text("Go to Settings")
//        }
//    }
//}
////
////@Composable
////fun ProfileSettingsScreen(onNavigateToNotifications: () -> Unit, onNavigateBackToHome: () -> Unit) {
////    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
////        Text("Profile Settings Screen")
////        Button(onClick = onNavigateToNotifications) {
////            Text("Go to Notification Settings")
////        }
////        Button(onClick = onNavigateBackToHome) {
////            Text("Back to Home (Example)")
////        }
////    }
////}
////
////@Composable
////fun NotificationSettingsScreen(onNavigateBackToProfile: () -> Unit) {
////    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
////        Text("Notification Settings Screen")
////        Button(onClick = onNavigateBackToProfile) {
////            Text("Back to Profile Settings")
////        }
////    }
////}
//
//
//


// --- Define Routes for Drawer and Sections ---
object MainAppDestinations {
    const val FIVE_CROWNS_GRAPH = "fiveCrownsGraph"
    const val SETTINGS_GRAPH = "settingsGraph"
    const val ABOUT_SCREEN = "aboutScreen"
}

// --- Define Routes for within Five Crowns (example) ---
object FiveCrownsDestinations {
    const val LANDING = "fiveCrownsLanding" // Renamed to avoid clash if you have a global landing
    const val ADD_PLAYERS = "fiveCrownsAddPlayers"
    const val GAME = "fiveCrownsGame"
    const val FINAL_SCORES = "fiveCrownsFinalScores"
}

// --- Define Routes for within Settings (example) ---
object SettingsDestinations {
    const val PROFILE = "settingsProfile"
    const val NOTIFICATIONS = "settingsNotifications"
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
        val currentRoute = mainNavController.currentBackStackEntryAsState().value?.destination?.route

        // Determine which TopAppBar to show or if to show one at all
        // This is a simplified example. You might need more complex logic.
        val showMainTopAppBar = currentRoute != FiveCrownsDestinations.ADD_PLAYERS &&
                currentRoute != FiveCrownsDestinations.GAME // etc.

        val drawerItems = listOf(
            DrawerMenuItem(MainAppDestinations.FIVE_CROWNS_GRAPH, "Five Crowns", Icons.Filled.AccountBox),
            DrawerMenuItem(MainAppDestinations.SETTINGS_GRAPH, "Settings", Icons.Filled.Settings),
            DrawerMenuItem(MainAppDestinations.ABOUT_SCREEN, "About", Icons.Filled.Info)
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
                    // Conditionally show TopAppBar based on the current route within a nested graph
                    // This is where you might want to pass down callbacks or observe NavController
                    // from nested graphs if they need to control the TopAppBar.
                    // For simplicity, we use the mainNavController's current route.
                    if (showMainTopAppBar) {
                        TopAppBar(
                            title = {
                                // Title could change based on the current section
                                val title = when {
                                    currentRoute?.startsWith(MainAppDestinations.FIVE_CROWNS_GRAPH) == true -> "Five Crowns"
                                    currentRoute?.startsWith(MainAppDestinations.SETTINGS_GRAPH) == true -> "Settings"
                                    currentRoute == MainAppDestinations.ABOUT_SCREEN -> "About"
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
    // GameViewModel might be scoped here if shared across all of Five Crowns,
    // or provided specifically to the fiveCrownsGraph
    val gameViewModel: GameViewModel = viewModel() // Or hiltViewModel()

    NavHost(
        navController = mainNavController,
        startDestination = MainAppDestinations.FIVE_CROWNS_GRAPH, // Your main start screen
        modifier = modifier
    ) {
        // --- Five Crowns Section (as a Nested Graph) ---
        navigation(
            route = MainAppDestinations.FIVE_CROWNS_GRAPH,
            startDestination = FiveCrownsDestinations.LANDING
        ) {
            fiveCrownsGraph(navController = mainNavController, gameViewModel = gameViewModel)
        }

        // --- Settings Section (as a Nested Graph) ---
        navigation(
            route = MainAppDestinations.SETTINGS_GRAPH,
            startDestination = SettingsDestinations.PROFILE
        ) {
            settingsGraph(navController = mainNavController)
        }

        // --- About Screen (Direct Composable) ---
        composable(MainAppDestinations.ABOUT_SCREEN) {
            AboutScreen(mainNavController) // Pass navController if it needs to navigate back or elsewhere
        }
    }
}

// --- Extension function to define the Five Crowns graph ---
fun NavGraphBuilder.fiveCrownsGraph(navController: NavHostController, gameViewModel: GameViewModel) {
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

// --- Extension function to define the Settings graph ---
fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    composable(SettingsDestinations.PROFILE) {
        ProfileSettingsScreen(
            onNavigateToNotifications = { navController.navigate(SettingsDestinations.NOTIFICATIONS) },
            onNavigateBackToHome = { // This should pop the settings graph
                navController.popBackStack(MainAppDestinations.SETTINGS_GRAPH, inclusive = true)
                // Then potentially navigate to the main app's start destination if needed
                // navController.navigate(MainAppDestinations.FIVE_CROWNS_GRAPH) { popUpTo ... }
            }
        )
    }
    composable(SettingsDestinations.NOTIFICATIONS) {
        NotificationSettingsScreen(
            onNavigateBackToProfile = { navController.popBackStack() }
        )
    }
}

// --- Dummy Screens for illustration ---
@Composable
fun AboutScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("About This App")
    }
}

@Composable
fun ProfileSettingsScreen(onNavigateToNotifications: () -> Unit, onNavigateBackToHome: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Profile Settings")
        Button(onClick = onNavigateToNotifications) { Text("Go to Notification Settings") }
        Button(onClick = onNavigateBackToHome) { Text("Back to Main (Pop Settings Graph)") }
    }
}

@Composable
fun NotificationSettingsScreen(onNavigateBackToProfile: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Notification Settings")
        Button(onClick = onNavigateBackToProfile) { Text("Back to Profile") }
    }
}

// You'll need GameViewModel.resetGameAndPlayers() or similar
fun GameViewModel.resetGameAndPlayers() {
    // Implementation to clear players, scores, round, wildCard, etc.
    this.players.clear() // Example
    this.resetScores()
    this.resetWildCard()
}

