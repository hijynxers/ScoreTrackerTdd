package com.grapevineindustries.plunder

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grapevineindustries.common.composables.STButton
import kotlin.random.Random

@Composable
fun PlunderScreen(onNavigateBackToHome: () -> Unit) {
    BackHandler(onBack = onNavigateBackToHome)
    val column = rememberSaveable { mutableIntStateOf(1) }
    val row = rememberSaveable { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Plunder",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(Modifier.height(16.dp))

        STButton(
            onClick = {
                row.intValue = Random.nextInt(1, 13)
                column.intValue = Random.nextInt(1, 19)
            },
            text = "Generate Treasure Location",
            testTag = ""
        )
        Text(mapIntToUppercaseChar(row.intValue) + column.intValue.toString())
    }
}

fun mapIntToUppercaseChar(number: Int): Char {
    return ('A'.code + (number - 1)).toChar()
}