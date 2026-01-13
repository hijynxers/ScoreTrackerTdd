package com.grapevineindustries.fivecrowns.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import com.grapevineindustries.fivecrowns.data.Player
import com.grapevineindustries.fivecrowns.testutils.AddPlayersTestUtils
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddPlayersUiTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var startGameClicked = false
    private var backClicked = false
    private var player = Player("")
    private var index = 0

    private val addPlayersUtils = AddPlayersTestUtils(composeTestRule)

    fun launchScreen() {
        composeTestRule.setContent {
            AddPlayersScreen(
                onStartGameClicked = { startGameClicked = true },
                onBackPress = { backClicked = true },
                updatePlayer = { index, player ->
                    this.player = player
                    this.index = index
                },
                players = listOf(
                    Player(""),
                    Player(""),
                    Player("")
                )
            )
        }
    }

    @Test
    fun clicked_start_game_button() {
        launchScreen()
        assertFalse(startGameClicked)

        addPlayersUtils.clickStartGame()
        assertTrue(startGameClicked)
    }

    @Test
    fun clicked_back_button() {
        launchScreen()
        assertFalse(backClicked)

        Espresso.pressBack()
        assertTrue(backClicked)
    }

    @Test
    fun input_is_recorded_in_text_field() {
        launchScreen()
        val expectedText = "name 1"
        addPlayersUtils.assertScreenShowing()

        addPlayersUtils.addPlayerNameText(
            index = 0,
            text = expectedText
        )
        assert(player == Player(name = expectedText))
    }
}