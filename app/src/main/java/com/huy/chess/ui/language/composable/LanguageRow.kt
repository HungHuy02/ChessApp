package com.huy.chess.ui.language.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageRow(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick()
            }.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(16.dp))
        Switch(
            checked = checked,
            onCheckedChange = { onClick() }
        )
    }
}
