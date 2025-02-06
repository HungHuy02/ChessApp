package com.huy.chess.ui.setupbot.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun PieceSelect(modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(24.dp), modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.wking_w),
            contentDescription = "white piece",
            tint = Color.Unspecified,
            modifier = Modifier.clip(MaterialTheme.shapes.small)
        )
        Icon(
            painter = painterResource(R.drawable.white_black),
            contentDescription = "random piece",
            tint = Color.Unspecified,
            modifier = Modifier.clip(MaterialTheme.shapes.small)
        )
        Icon(
            painter = painterResource(R.drawable.bking_b),
            contentDescription = "black piece",
            tint = Color.Unspecified,
            modifier = Modifier.clip(MaterialTheme.shapes.small)
        )
    }
}