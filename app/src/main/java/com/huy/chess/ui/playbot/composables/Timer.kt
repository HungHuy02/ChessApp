package com.huy.chess.ui.playbot.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.utils.Formatters

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    time: Long,
    isWhite: Boolean
) {
    Text(
        text = Formatters.formatSmartTime(time),
        textAlign = TextAlign.End,
        modifier = modifier
            .width(60.dp)
            .background(if(isWhite) Color.Black else Color.Blue)
            .padding(vertical = 2.dp)
    )
}

@Preview
@Composable
private fun Preview() {
    Timer(
        time = 30000000123,
        isWhite = false
    )
}