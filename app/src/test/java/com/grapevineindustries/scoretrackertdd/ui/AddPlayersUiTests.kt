package com.grapevineindustries.scoretrackertdd.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import com.grapevineindustries.scoretrackertdd.Player
import com.grapevineindustries.scoretrackertdd.utils.AddPlayersTestUtils
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddPlayersUiTests {
    @JvmField
    @Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private var startGameClicked = false
    private var backClicked = false
    private var player = Player("")
    private var index = 0

    private val addPlayersUtils = AddPlayersTestUtils(composeTestRule)

    @Before
    fun setup() {
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
        assertFalse(startGameClicked)

        addPlayersUtils.clickStartGame()
        assertTrue(startGameClicked)
    }

    @Test
    fun clicked_back_button() {
        assertFalse(backClicked)

        Espresso.pressBack()
        assertTrue(backClicked)
    }

    @Test
    fun input_is_recorded_in_text_field() {
        val expectedText = "name 1"
        addPlayersUtils.assertScreenShowing()

        addPlayersUtils.addPlayerNameText(
            index = 0,
            text = expectedText
        )
        assert(player == Player(name = expectedText))
    }
}