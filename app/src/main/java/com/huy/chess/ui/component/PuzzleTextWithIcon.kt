package com.huy.chess.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun PuzzleTextWithIcon(
    icon: Painter,
    text: String,
    textColor: Color = Color.Black
) {
    Icon(
        painter = icon,
        contentDescription = "icon",
        tint = Color.Unspecified
    )
    Spacer(Modifier.width(8.dp))
    Text(
        text = text,
        color = textColor,
        style = MaterialTheme.typography.labelMedium
    )
}