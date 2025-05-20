package com.huy.chess.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huy.chess.R

@Composable
fun PlayerArea(
    modifier: Modifier = Modifier,
    name: String,
    avatar: String = "",
    map: MutableMap<Char, Int> = mutableMapOf(),
    side: Boolean,
    list: List<ImageBitmap> = emptyList(),
    time: Long? = null,
    isRun: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = avatar,
            contentDescription = "image",
            error = painterResource(R.drawable.noavatar),
            modifier = Modifier.size(48.dp)
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = name
            )
            CapturedPiece(
                map = map,
                list = list,
                side = side
            )
        }
        time?.let {
            Spacer(Modifier.weight(1f))
            Timer(
                time = it,
                isWhite = side,
                isRun = isRun
            )
        }
    }


}