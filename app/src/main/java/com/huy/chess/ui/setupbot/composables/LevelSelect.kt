package com.huy.chess.ui.setupbot.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun LevelSelect(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        OneLevel(text = stringResource(R.string.one_text))
        OneLevel(text = stringResource(R.string.two_text))
        OneLevel(text = stringResource(R.string.three_text))
        OneLevel(text = stringResource(R.string.four_text))
        OneLevel(text = stringResource(R.string.five_text))
        OneLevel(text = stringResource(R.string.six_text))
        OneLevel(text = stringResource(R.string.sevent_text))
        OneLevel(text = stringResource(R.string.eight_text))
    }
}

@Composable
fun OneLevel(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}