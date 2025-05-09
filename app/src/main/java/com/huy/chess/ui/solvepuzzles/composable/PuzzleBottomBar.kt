package com.huy.chess.ui.solvepuzzles.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@Composable
fun PuzzleBottomBar(
    modifier: Modifier = Modifier,
    type: PuzzleDescriptionType,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        when(type) {
            PuzzleDescriptionType.BlackMove,
            PuzzleDescriptionType.WhiteMove -> {
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
            PuzzleDescriptionType.Correct -> {
                Item(
                    icon = "\u0394",
                    title = stringResource(R.string.analysis_text)
                )
                Item(
                    icon = "\u004C",
                    title = stringResource(R.string.start_over_text)
                )
                Item(
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.continue_text),
                    onClick = {}
                )
            }
            PuzzleDescriptionType.Fail -> {
                Item(
                    icon = "\u1F4C",
                    title = stringResource(R.string.answer_text)
                )
                Item(
                    icon = "\u005D",
                    title = stringResource(R.string.continue_text)
                )
                Item(
                    color = MaterialTheme.colorScheme.error,
                    text = stringResource(R.string.try_text),
                    onClick = {}
                )
            }
        }

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

@Composable
private fun RowScope.Item(
    modifier: Modifier = Modifier,
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    AppButton(
        modifier = modifier.weight(1f),
        onClick = onClick,
        text = text,
        backgroundColor = color,
        iconPosition = IconPosition.NONE
    )
}