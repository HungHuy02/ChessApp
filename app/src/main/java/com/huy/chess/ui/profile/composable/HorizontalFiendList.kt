package com.huy.chess.ui.profile.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huy.chess.R

val test = listOf(
    "" to "test",
    "" to "test",
    "" to "test",
    "" to "test",
    "" to "test",
    "" to "test"
)

@Composable
fun HorizontalFriendList(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(test.size) {
            FriendItem(
                image = test[it].first,
                name = test[it].second
            )
        }
    }
}

@Composable
fun FriendItem(
    modifier: Modifier = Modifier,
    image: String?,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
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
            text = name,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    HorizontalFriendList()
}

