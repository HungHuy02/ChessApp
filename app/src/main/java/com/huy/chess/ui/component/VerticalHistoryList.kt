package com.huy.chess.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huy.chess.R

@Composable
fun VerticalHistoryList(
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
    ) {
        items(5) {
            VerticalHistoryItem(
                iconTime = painterResource(R.drawable.timer_24px),
                image = null,
                name = "test",
                elo = 800,
                result = "0-1"
            )
        }
    }
}

@Composable
fun VerticalHistoryItem(
    modifier: Modifier = Modifier,
    iconTime: Painter,
    image: String?,
    name: String,
    elo: Int,
    result: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(16.dp)
            .clickable {  }
    ) {
        Icon(
            painter = iconTime,
            contentDescription = "icon time"
        )
        if (image.isNullOrEmpty()) {
            Image(
                painter = painterResource(R.drawable.noavatar),
                contentDescription = "image"
            )
        } else {
            AsyncImage(
                model = image,
                contentDescription = "image",
                error = painterResource(R.drawable.noavatar)
            )
        }
        Text(
            text = stringResource(R.string.name_with_elo_text, name, elo),
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = when(result) {
                "1-0" -> "+"
                "0-1" -> "-"
                else -> result },
            modifier = Modifier
                .background(Color.Red)
                .padding(2.dp)

        )
        Icon(
            painter = painterResource(R.drawable.analysis),
            contentDescription = "analysis icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
@Preview
private fun Preview() {
    VerticalHistoryList()
}