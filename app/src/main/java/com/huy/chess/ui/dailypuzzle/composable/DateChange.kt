package com.huy.chess.ui.dailypuzzle.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun DateChange(
    modifier: Modifier = Modifier,
    date: String,
    onAction: () -> Unit
) {
    val size = LocalConfiguration.current.screenWidthDp * 0.5
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(size.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onAction() }
    ) {
        Icon(
            painter = painterResource(R.drawable.chevron_left_24px),
            contentDescription = "left icon",
            modifier = Modifier.clickable {

            }
        )

        Text(
            text = date,
        )

        Icon(
            painter = painterResource(R.drawable.chevron_right_24px),
            contentDescription = "right icon",
            modifier = Modifier.clickable {

            }
        )
    }
}