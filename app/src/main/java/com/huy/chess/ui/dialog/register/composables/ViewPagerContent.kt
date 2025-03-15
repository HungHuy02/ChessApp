package com.huy.chess.ui.dialog.register.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun ViewPagerContent(
    title: Int,
    subTitle: Int,
    description: Int,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(title),
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(R.drawable.wpawn),
            contentDescription = "image"
        )
        Text(
            text = stringResource(subTitle)
        )
        Text(
            text = stringResource(description),
            textAlign = TextAlign.Center
        )
    }
}