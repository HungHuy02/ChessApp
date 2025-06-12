package com.huy.chess.ui.solvepuzzles.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesAction
import com.huy.chess.ui.theme.ChessGlyphFontFamily

@Composable
fun PuzzleBottomBar(
    modifier: Modifier = Modifier,
    type: PuzzleDescriptionType,
    onClick: (SolvePuzzlesAction) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when(type) {
            PuzzleDescriptionType.BlackMove,
            PuzzleDescriptionType.WhiteMove -> {
                Item(
                    icon = "\u0067",
                    title = stringResource(R.string.suggest_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedSuggestion) }
                )
                Item(
                    icon = "\u002C",
                    title = stringResource(R.string.back_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedBack) }
                )
                Item(
                    icon = "\u2026",
                    title = stringResource(R.string.continue_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedForward) }
                )
            }
            PuzzleDescriptionType.Correct -> {
                Item(
                    icon = "\u0394",
                    title = stringResource(R.string.analysis_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedAnalysis) }
                )
                Item(
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.continue_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedContinue) }
                )
                Item(
                    icon = "\u004C",
                    title = stringResource(R.string.start_over_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedRetry) }
                )
            }
            PuzzleDescriptionType.Fail -> {
                Item(
                    icon = "\u1F4C",
                    title = stringResource(R.string.answer_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedAnswer) }
                )
                Item(
                    color = MaterialTheme.colorScheme.error,
                    text = stringResource(R.string.try_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedRetry) }
                )
                Item(
                    icon = "\u005D",
                    title = stringResource(R.string.continue_text),
                    onClick = { onClick(SolvePuzzlesAction.ClickedContinue) }
                )
            }

            PuzzleDescriptionType.NONE -> {}
        }

    }
}

@Composable
private fun RowScope.Item(
    modifier: Modifier = Modifier,
    icon: String,
    title: String,
    onClick: () -> Unit
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
            .weight(1f)
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp)
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
        modifier = modifier.weight(2f),
        onClick = onClick,
        text = text,
        backgroundColor = color,
        backgroundTopColor = color,
        backgroundBottomColor = color,
        iconPosition = IconPosition.NONE
    )
}