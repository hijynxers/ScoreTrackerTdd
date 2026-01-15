package com.grapevineindustries.fivecrowns.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun HighLowScoreScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(HighLowScoreScreenTestTags.ROOT),
        contentAlignment = Alignment.Center
    ) {
        Text("High Low Score Screen") // Placeholder
    }
}

object HighLowScoreScreenTestTags {
    const val ROOT = "HighLowScoreScreen_ROOT"
}