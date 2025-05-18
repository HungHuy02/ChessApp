package com.huy.chess.ui.play.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@Composable
fun PlayScreenBottomBar(
    modifier: Modifier = Modifier,
    isEnd: Boolean = false,
    onClick: (PlayAction) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {
        PlayScreenBottomBarItem(
            icon = "\u1F85",
            text = stringResource(R.string.options_text),
            onClick = { onClick(PlayAction.ClickedMore) }
        )
        if(isEnd)
            PlayScreenBottomBarItem(
                icon = "\u0394",
                text = stringResource(R.string.analysis_text),
                onClick = { onClick(PlayAction.ClickedAnalysis) }
            )
        PlayScreenBottomBarItem(
            icon = "\u002C",
            text = stringResource(R.string.back_text),
            onClick = { onClick(PlayAction.ClickedBack) }
        )
        PlayScreenBottomBarItem(
            icon = "\u2026",
            text = stringResource(R.string.forward_text),
            onClick = { onClick(PlayAction.ClickedForward) }
        )
    }
}

@Composable
fun RowScope.PlayScreenBottomBarItem(
    modifier: Modifier = Modifier,
    icon: String,
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily, fontSize = 24.sp)) {
                appendLine(icon)
            }
            withStyle(SpanStyle()) {
                append(text)
            }
        },
        textAlign = TextAlign.Center,
        modifier = modifier
            .weight(1f)
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp)
    )
}