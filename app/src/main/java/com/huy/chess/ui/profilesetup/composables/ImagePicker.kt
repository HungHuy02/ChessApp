package com.huy.chess.ui.profilesetup.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun ImagePicker(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Gray)
    ) {
        Icon(
            painter = painterResource(R.drawable.add_a_photo_24px),
            contentDescription = "add a photo icon",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(48.dp)
                .padding(8.dp)
        )
    }
}

@Composable
@Preview
fun Preview() {
    ImagePicker()
}