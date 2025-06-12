package com.huy.chess.ui.solvepuzzles.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.PuzzleTextWithColor
import com.huy.chess.ui.component.PuzzleTextWithIcon

sealed class PuzzleDescriptionType {
    data object NONE: PuzzleDescriptionType()
    data object BlackMove: PuzzleDescriptionType()
    data object WhiteMove: PuzzleDescriptionType()
    data object Fail: PuzzleDescriptionType()
    data object Correct: PuzzleDescriptionType()
}

@Composable
fun PuzzleDescription(
    modifier: Modifier = Modifier,
    type: PuzzleDescriptionType,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
            .padding(8.dp)
    ) {
        when(type) {
            PuzzleDescriptionType.BlackMove -> {
                PuzzleTextWithColor(
                    color = Color.Black,
                    text = stringResource(R.string.black_player_turn_text)
                )
            }
            PuzzleDescriptionType.Correct -> {
                PuzzleTextWithIcon(
                    icon = painterResource(R.drawable.correct),
                    text = stringResource(R.string.puzzle_solved_text)
                )
            }
            PuzzleDescriptionType.Fail -> {
                PuzzleTextWithIcon(
                    icon = painterResource(R.drawable.fail),
                    text = stringResource(R.string.wrong_text)
                )
            }
            PuzzleDescriptionType.WhiteMove -> {
                PuzzleTextWithColor(
                    color = Color.White,
                    text = stringResource(R.string.white_player_turn_text)
                )
            }
            PuzzleDescriptionType.NONE -> {}
        }
    }
}

