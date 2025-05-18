package com.huy.chess.ui.home.composables

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.component.ChessBoard

@Composable
fun HomeItems(
    fen: String,
    list: List<Bitmap>,
    boardSizeDp: Int,
    boardBitmap: Bitmap,
    title: String,
    description: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    ) {
        ChessBoard(
            list = list,
            fen = fen,
            boardSizeDp = boardSizeDp,
            boardBitmap = boardBitmap,
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}