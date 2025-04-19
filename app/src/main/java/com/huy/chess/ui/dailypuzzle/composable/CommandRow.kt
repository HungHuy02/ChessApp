package com.huy.chess.ui.dailypuzzle.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.huy.chess.R

@Composable
fun CommandRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.chevron_right_24px),
            contentDescription = "icon"
        )
        Text(
            text = stringResource(R.string.edit_text)
        )
    }
}