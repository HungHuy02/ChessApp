package com.huy.chess.ui.newgame.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.theme.Gray700
import com.huy.chess.ui.theme.Gray900
import com.huy.chess.ui.theme.TransparentBlack65

@Composable
fun NewGameButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    painter: Painter,
    enable: Boolean = true
) {
    Row(
        modifier = modifier
            .background(color = Gray700, shape = MaterialTheme.shapes.medium)
            .padding(top = 0.6.dp)
            .background(color = TransparentBlack65, shape = MaterialTheme.shapes.medium)
            .padding(bottom = 2.dp)
            .background(color = Gray900, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .then(
                if(enable) Modifier
                    .clickable {
                        onClick()
                    }
                else Modifier
            )
    ) {
        Spacer(Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(2f).padding(vertical = 4.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = "icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
        }
        Spacer(Modifier.weight(1f))
    }
}