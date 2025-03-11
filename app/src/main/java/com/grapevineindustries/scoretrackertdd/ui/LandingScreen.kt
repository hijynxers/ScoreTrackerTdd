package com.grapevineindustries.scoretrackertdd.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants.DEFAULT_NUM_PLAYERS
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants.MAX_PLAYERS
import com.grapevineindustries.scoretrackertdd.FiveCrownsConstants.MIN_PLAYERS
import com.grapevineindustries.scoretrackertdd.R
import com.grapevineindustries.scoretrackertdd.theme.Dimen
import com.grapevineindustries.scoretrackertdd.theme.ScoreTrackerTheme
import com.grapevineindustries.scoretrackertdd.ui.LandingScreenTestTags.NAVIGATION_DRAWER_BUTTON
import com.grapevineindustries.scoretrackertdd.ui.NavigationDrawerTestTags.CONTENT
import com.grapevineindustries.scoretrackertdd.ui.NavigationDrawerTestTags.FIVE_CROWNS
import com.grapevineindustries.scoretrackertdd.ui.composables.STButton
import kotlinx.coroutines.launch

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LandingPreview() {
    LandingScreen(
        onAddPlayersClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    onAddPlayersClick: (Int) -> Unit
) {
    ScoreTrackerTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    onFiveCrownsClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
//                        updateGame(GameOption.FIVE_CROWNS)
                    },
                    onRummyClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
//                        updateGame(GameOption.RUMMY)
                    }
                )
            },
            drawerState = drawerState,
            content = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Score Tracker") },
                            navigationIcon = {
                                IconButton(
                                    modifier = Modifier.testTag(NAVIGATION_DRAWER_BUTTON),
                                    onClick = {
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            }
                        )
                    },
                    content = { paddingValues ->
                        val numPlayers =
                            remember { mutableIntStateOf(DEFAULT_NUM_PLAYERS) }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    top = paddingValues.calculateTopPadding(),
                                    bottom = paddingValues.calculateBottomPadding(),
                                    start = Dimen.outerScreenPadding,
                                    end = Dimen.outerScreenPadding
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.weight(2f))

                            Text(
                                modifier = Modifier.testTag(LandingScreenTestTags.GAME_TITLE),
                                text = getGameTitle(GameOption.FIVE_CROWNS),
                                style = MaterialTheme.typography.displayMedium
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = stringResource(id = R.string.num_players),
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(
                                    modifier = Modifier.testTag(LandingScreenTestTags.NUM_PLAYERS_MINUS),
                                    onClick = {
                                        if (numPlayers.intValue > MIN_PLAYERS) {
                                            numPlayers.intValue -= 1
                                        }
                                    },
                                    content = {
                                        Text(
                                            text = "-",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )

                                Text(
                                    modifier = Modifier.testTag(LandingScreenTestTags.NUM_PLAYERS),
                                    text = numPlayers.intValue.toString()
                                )

                                TextButton(
                                    modifier = Modifier.testTag(LandingScreenTestTags.NUM_PLAYERS_ADD),
                                    onClick = {
                                        if (numPlayers.intValue < MAX_PLAYERS) {
                                            numPlayers.intValue += 1
                                        }
                                    },
                                    content = {
                                        Text(
                                            text = "+",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.weight(2f))

                            STButton(
                                onClick = {
                                    onAddPlayersClick(numPlayers.intValue)
                                },
                                text = stringResource(id = R.string.enter_players),
                                testTag = LandingScreenTestTags.ADD_PLAYERS_BUTTON,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                )
            }
        )
    }
}

@Composable
fun NavigationDrawer(
    onFiveCrownsClick: () -> Unit,
    onRummyClick: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.testTag(CONTENT),
        content = {
            Column {
                TextButton(
                    modifier = Modifier.testTag(FIVE_CROWNS),
                    onClick = onFiveCrownsClick
                ) {
                    Text(text = "Five Crowns")
                }
//                TextButton(
//                    modifier = Modifier.testTag(RUMMY),
//                    onClick = onRummyClick
//                ) {
//                    Text(text = "Rummy")
//                }
            }
        }
    )
}

fun getGameTitle(game: GameOption): String {
    return when (game) {
        GameOption.FIVE_CROWNS -> {
            "Five Crowns"
        }

        GameOption.RUMMY -> {
            "Rummy"
        }
    }
}

object LandingScreenTestTags {
    const val GAME_TITLE = "GAME_TITLE"
    const val NUM_PLAYERS = "NUM_PLAYERS"
    const val NUM_PLAYERS_ADD = "NUM_PLAYERS_ADD"
    const val NUM_PLAYERS_MINUS = "NUM_PLAYERS_MINUS"
    const val ADD_PLAYERS_BUTTON = "ADD_PLAYERS_BUTTON"
    const val NAVIGATION_DRAWER_BUTTON = "NAVIGATION_BUTTON"
}

object NavigationDrawerTestTags {
    const val CONTENT = "NAV_DRAWER_CONTENT"
    const val FIVE_CROWNS = "NAV_DRAWER_FIVE_CROWNS_BUTTON"
    const val RUMMY = "NAV_DRAWER_RUMMY_BUTTON"
}

enum class GameOption {
    FIVE_CROWNS,
    RUMMY
}