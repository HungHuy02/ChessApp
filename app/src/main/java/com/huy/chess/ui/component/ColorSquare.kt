package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColorSquare(
    modifier: Modifier = Modifier,
    color: Color,
    size: Dp = 24.dp
) {
    Box(
        modifier
        .size(size)
        .background(color = color, shape = RoundedCornerShape(2.dp))
        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(2.dp))
    )
}