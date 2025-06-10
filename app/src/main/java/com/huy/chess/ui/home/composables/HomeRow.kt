package com.huy.chess.ui.home.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.huy.chess.ui.theme.ChessSansFontFamily

@Composable
fun HomeRow(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontFamily = ChessSansFontFamily
        )
    }
}