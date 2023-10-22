package com.grapevineindustries.scoretrackertdd

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPlayersScreen() {
    Scaffold(
        content = {
            Text(
                modifier = Modifier.testTag(AddPlayersScreenTestTags.TestTag),
                text = "This is some content")
        }
    )
}

object AddPlayersScreenTestTags {
    const val TestTag = "TESTTAG"
}