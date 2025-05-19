package com.huy.chess.ui.newgame.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.ui.theme.Gray700
import com.huy.chess.utils.enums.TimeType
import com.huy.chess.utils.toPair

@Composable
fun TimeButton(
    modifier: Modifier = Modifier,
    timeType: TimeType,
    onClick: () -> Unit
) {
    val pair = timeType.toPair()
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .background(Gray700, shape = MaterialTheme.shapes.small)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .clip(MaterialTheme.shapes.small),
        ) {
        Icon(
            painter = painterResource(pair.first),
            contentDescription = "icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(pair.second),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "\u2026",
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = ChessGlyphFontFamily,
            fontSize = 20.sp
        )
    }
}