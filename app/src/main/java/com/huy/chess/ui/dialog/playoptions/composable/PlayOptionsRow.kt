package com.huy.chess.ui.dialog.playoptions.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun PlayOptionsRow(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    isEnable: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(textId),
            color = if(isEnable) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
        )
    }
}