package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huy.chess.R
import com.huy.chess.data.database.entities.HistoryEntity
import com.huy.chess.utils.Utils

@Composable
fun VerticalHistoryList(
    modifier: Modifier = Modifier,
    list: List<HistoryEntity>
) {
    LazyColumn (
        modifier = modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list) {
            VerticalHistoryItem(
                iconTime = painterResource(Utils.getTimeControlDrawableRes(it.timeControl)),
                image = "",
                name = it.black,
                elo = it.blackElo,
                result = it.result,
                onClick = {}
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
    result: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Icon(
            painter = iconTime,
            contentDescription = "icon time",
            tint = Color.Unspecified,
            modifier = Modifier.size(40.dp)
        )
        AsyncImage(
            model = image,
            contentDescription = "image",
            error = painterResource(R.drawable.noavatar),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(48.dp)
        )
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
                .background(when(result) {
                    "1-0" -> Color.Green
                    "0-1" -> Color.Red
                    else -> Color.DarkGray})

        )
        Icon(
            painter = painterResource(R.drawable.analysis),
            contentDescription = "analysis icon",
            modifier = Modifier.size(24.dp)
        )
    }
}