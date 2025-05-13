package com.huy.chess.ui.setupbot.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.utils.enums.Side

@Composable
fun PieceSelect(
    modifier: Modifier = Modifier,
    side: Side,
    onClick: (Side) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        Item(
            painter = painterResource(R.drawable.wking_w),
            isSelected = side == Side.WHITE,
            onClick = { onClick(Side.WHITE) }
        )
        Item(
            painter = painterResource(R.drawable.white_black),
            isSelected = side == Side.RANDOM,
            onClick = { onClick(Side.RANDOM) }
        )
        Item(
            painter = painterResource(R.drawable.bking_b),
            isSelected = side == Side.BLACK,
            onClick = { onClick(Side.BLACK) }
        )
    }
}

@Composable
private fun Item(
    painter: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Icon(
        painter = painter,
        contentDescription = "icon",
        tint = Color.Unspecified,
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .then(
                if(isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small
                    )
                } else
                    Modifier
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
    )
}