package com.huy.chess.ui.dailypuzzle.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.PuzzleTextWithColor
import com.huy.chess.ui.component.PuzzleTextWithIcon
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.ui.theme.Gray200
import com.huy.chess.ui.theme.Gray700
import com.huy.chess.utils.enums.PuzzleStatus

@Composable
fun DescriptionText(
    modifier: Modifier = Modifier,
    puzzleStatus: PuzzleStatus,
    move: String
) {
    Row(
        modifier = modifier
            .background(
                if(puzzleStatus == PuzzleStatus.FINISH) MaterialTheme.colorScheme.primary
                else Gray700,
                shape = MaterialTheme.shapes.medium)
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when(puzzleStatus) {
            PuzzleStatus.START_WHITE -> {
                PuzzleTextWithColor(
                    text = stringResource(R.string.white_player_turn_text),
                    color = Color.White,
                    textColor = Color.White
                )
            }
            PuzzleStatus.START_BLACK-> {
                PuzzleTextWithColor(
                    text = stringResource(R.string.black_player_turn_text),
                    color = Color.Black,
                    textColor = Color.White
                )
            }
            PuzzleStatus.CORRECT_MOVE -> {
                PuzzleTextWithIcon(
                    icon = painterResource(R.drawable.correct),
                    text = stringResource(R.string.correct_answer_text, move),
                    textColor = MaterialTheme.colorScheme.primary
                )
            }
            PuzzleStatus.WRONG_MOVE -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily, color = MaterialTheme.colorScheme.error)) {
                            append("L ")
                        }
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
                            appendLine(stringResource(R.string.incorrect_answer_text, move))
                        }
                        withStyle(SpanStyle(color = Gray200)) {
                            append(stringResource(R.string.click_to_watch_video_text))
                        }
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily, color = Gray200)) {
                            append(" \u004A")
                        }
                    }
                )
            }
            PuzzleStatus.FINISH ->  {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                            append("G ")
                        }
                        withStyle(SpanStyle()) {
                            append(stringResource(R.string.solved_text))
                        }
                    }
                )
            }
        }
    }
}