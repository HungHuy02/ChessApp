package com.huy.chess.ui.dailypuzzle.composable

import androidx.annotation.StringRes
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huy.chess.R
import com.huy.chess.ui.dailypuzzle.DailyPuzzleAction
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.utils.enums.PuzzleStatus

@Composable
fun DailyPuzzleBottomBar(
    modifier: Modifier = Modifier,
    puzzleStatus: PuzzleStatus,
    onClick: (DailyPuzzleAction) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        when(puzzleStatus) {
            PuzzleStatus.START_WHITE,
            PuzzleStatus.START_BLACK,
            PuzzleStatus.CORRECT_MOVE -> {
                DailyPuzzleScreenBottomBarItem (
                    icon = "\u0067",
                    text = stringResource(R.string.suggest_text),
                    onClick = { onClick(DailyPuzzleAction.ClickedHint) }
                )
            }
            PuzzleStatus.WRONG_MOVE -> {
                DailyPuzzleScreenBottomBarItem(
                    icon = "\u0028",
                    text = stringResource(R.string.answer_text),
                    onClick = { onClick(DailyPuzzleAction.ClickedHint) }
                )
                DailyPuzzleScreenBottomBarItem(
                    icon = "\u004C",
                    text = stringResource(R.string.try_again_text),
                    color = MaterialTheme.colorScheme.error,
                    onClick = { onClick(DailyPuzzleAction.ClickedHint) }
                )
            }
            PuzzleStatus.FINISH -> {
                DailyPuzzleScreenBottomBarItem.itemList.forEach {
                    DailyPuzzleScreenBottomBarItem(
                        icon = it.icon,
                        text = stringResource(it.label),
                        onClick = { onClick(it.action) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.DailyPuzzleScreenBottomBarItem(
    modifier: Modifier = Modifier,
    icon: String,
    text: String,
    color: Color = Color.White,
    onClick: () -> Unit
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily,  fontSize = 24.sp, color = color)) {
                appendLine(icon)
            }
            withStyle(SpanStyle(color = color)) {
                append(text)
            }
        },
        textAlign = TextAlign.Center,
        modifier = modifier.weight(1f)
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp)
    )
}

sealed class DailyPuzzleScreenBottomBarItem(
    val icon: String,
    @StringRes val label: Int,
    val action: DailyPuzzleAction
) {
    data object Reset : DailyPuzzleScreenBottomBarItem(
        icon = "\u004C",
        label = R.string.reset_board_text,
        action = DailyPuzzleAction.ClickedReset
    )

    data object Analysis : DailyPuzzleScreenBottomBarItem(
        icon = "\u0394",
        label = R.string.analysis_text,
        action = DailyPuzzleAction.ClickedAnalysis
    )

    data object Back : DailyPuzzleScreenBottomBarItem(
        icon = "\u002C",
        label = R.string.back_text,
        action = DailyPuzzleAction.ClickedBack
    )

    data object Forward : DailyPuzzleScreenBottomBarItem(
        icon = "\u2026",
        label = R.string.forward_text,
        action = DailyPuzzleAction.ClickedForward
    )

    companion object {
        val itemList = listOf(Reset, Analysis, Back, Forward)
    }
}