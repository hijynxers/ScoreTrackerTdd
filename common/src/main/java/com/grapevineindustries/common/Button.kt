package com.grapevineindustries.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.common.theme.ScoreTrackerTheme

@Preview(showBackground = true)
@Composable
private fun ButtonPreview() {
    ScoreTrackerTheme {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ){
            STButton(
                onClick = {},
                text = "Enter Players",
                testTag = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun STButton(
    onClick: () -> Unit,
    text: String,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.testTag(testTag),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}