package com.huy.chess.ui.solvepuzzles.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.R
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@Composable
fun PuzzleBottomBar(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Item(
            icon = "\u0067",
            title = stringResource(R.string.suggest_text)
        )
        Item(
            icon = "\u002C",
            title = stringResource(R.string.back_text)
        )
        Item(
            icon = "\u2026",
            title = stringResource(R.string.continue_text)
        )
    }
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    icon: String,
    title: String
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily, fontSize = 24.sp)) {
                appendLine(icon)
            }
            withStyle(SpanStyle()) {
                append(title)
            }
        },
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}