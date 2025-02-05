package com.huy.chess.ui.changetime.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowTimeButton(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String,
    text3: String
) {
    Row(modifier = modifier) {
        TimeButton(
            text = text1,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        TimeButton(
            text = text2,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        TimeButton(
            text = text3,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RowTimeButton(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    Row(modifier = modifier) {
        TimeButton(
            text = text1,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        TimeButton(
            text = text2,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Spacer(modifier = Modifier.weight(1f))
    }
}