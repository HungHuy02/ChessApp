package com.huy.chess.ui.dailypuzzle.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.utils.enums.PuzzleStatus

@Composable
fun DailyPuzzleBottomBar(
    modifier: Modifier = Modifier,
    puzzleStatus: PuzzleStatus,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        when(puzzleStatus) {
            PuzzleStatus.START,
            PuzzleStatus.CORRECT_MOVE -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                            appendLine('g')
                        }
                        withStyle(SpanStyle( )) {
                            append(stringResource(R.string.suggest_text))
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            PuzzleStatus.WRONG_MOVE -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                            appendLine('(')
                        }
                        withStyle(SpanStyle( )) {
                            append(stringResource(R.string.answer_text))
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                            appendLine('L')
                        }
                        withStyle(SpanStyle( )) {
                            append(stringResource(R.string.try_again_text))
                        }
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            PuzzleStatus.FINISH -> {
                PlayScreenBottomBarItem.itemList.forEach {
                    PlayScreenBottomBarItem(
                        painter = painterResource(it.icon),
                        text = stringResource(it.label),
                        onClick = { onClick() },
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
fun PlayScreenBottomBarItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        Icon(
            painter = painter,
            contentDescription = "icon"
        )
        Text(
            text = text
        )
    }
}

sealed class PlayScreenBottomBarItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val playAction: PlayAction
) {
    data object Options : PlayScreenBottomBarItem(
        icon = R.drawable.format_list_bulleted_24px,
        label = R.string.more_options_text,
        playAction = PlayAction.ClickedMore
    )

    data object Add : PlayScreenBottomBarItem(
        icon = R.drawable.add_24px,
        label = R.string.more_options_text,
        playAction = PlayAction.ClickedAdd
    )

    data object Back : PlayScreenBottomBarItem(
        icon = R.drawable.chevron_left_24px,
        label = R.string.back_text,
        playAction = PlayAction.ClickedBack
    )

    data object Forward : PlayScreenBottomBarItem(
        icon = R.drawable.chevron_right_24px,
        label = R.string.forward_text,
        playAction = PlayAction.ClickedForward
    )

    companion object {
        val itemList = listOf(Options, Add, Back, Forward)
    }
}