package com.huy.chess.ui.profilesetup.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huy.chess.R

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    uri: String?,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Gray)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = uri ?: { R.drawable.add_a_photo_24px },
            contentDescription = "add a photo icon",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(48.dp)
        )
    }
}