package com.huy.chess.ui.dailypuzzle.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.theme.ChessGlyphFontFamily
import com.huy.chess.utils.enums.PuzzleStatus
import com.huy.chess.R

@Composable
fun DescriptionText(
    modifier: Modifier = Modifier,
    puzzleStatus: PuzzleStatus,
    move: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when(puzzleStatus) {
            PuzzleStatus.START_WHITE,
            PuzzleStatus.START_BLACK-> {
                Box(Modifier.size(4.dp).background(color = Color.White))
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(
                        if(puzzleStatus == PuzzleStatus.START_WHITE) R.string.white_player_turn_text
                        else R.string.black_player_turn_text
                    )
                )
            }
            PuzzleStatus.CORRECT_MOVE -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                            append("G ")
                        }
                        withStyle(SpanStyle()) {
                            append(stringResource(R.string.correct_answer_text, move))
                        }
                    }
                )
            }
            PuzzleStatus.WRONG_MOVE -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = ChessGlyphFontFamily)) {
                            append("L ")
                        }
                        withStyle(SpanStyle()) {
                            append(stringResource(R.string.incorrect_answer_text, move))
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