import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.common.composables.ScoreTrackerAlertDialogTestTags

class ScoreTrackerAlertDialogTestUtils(
    private val composeTestRule: ComposeTestRule
) {

    fun assertShowing(
        title: String = "Title",
        text: String = "text",
        confirmButtonText: String = "OK",
        dismissButtonText: String? = null
    ) {
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.ALERT_DIALOG)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.TITLE)
            .assertIsDisplayed()
            .assertTextEquals(title)
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.TEXT)
            .assertIsDisplayed()
            .assertTextEquals(text)
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.CONFIRM_BUTTON, useUnmergedTree = true)
            .assertIsDisplayed()
            .onChild()
            .assertTextEquals(confirmButtonText)
        if (dismissButtonText == null) {
            composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.DISMISS_BUTTON)
                .assertDoesNotExist()
        } else {
            composeTestRule.onNodeWithTag(
                ScoreTrackerAlertDialogTestTags.DISMISS_BUTTON,
                useUnmergedTree = true
            )
                .assertIsDisplayed()
                .onChild()
                .assertTextEquals(dismissButtonText)
        }
    }

    fun assertNotShowing() {
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.ALERT_DIALOG)
            .assertDoesNotExist()
    }

    fun clickConfirmButton() {
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.CONFIRM_BUTTON)
            .performClick()
    }

    fun clickDismissButton() {
        composeTestRule.onNodeWithTag(ScoreTrackerAlertDialogTestTags.DISMISS_BUTTON)
            .performClick()
    }
}
