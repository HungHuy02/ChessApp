package com.huy.chess.ui.newgame.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.huy.chess.R

@Composable
fun TimeButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.timer_24px),
                contentDescription = "icon"
            )
            Text(
                text = stringResource(R.string.ten_minute_text),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                painter = painterResource(R.drawable.chevron_right_24px),
                contentDescription = "icon"
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TimeButton(modifier = Modifier.fillMaxWidth())
}