package com.huy.chess.ui.solvepuzzles.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.ColorSquare

sealed class PuzzleDescriptionType {
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
                TextWithColor(
                    color = Color.Black,
                    text = stringResource(R.string.black_player_turn_text)
                )
            }
            PuzzleDescriptionType.Correct -> {
                TextWithIcon(
                    icon = painterResource(R.drawable.correct),
                    text = stringResource(R.string.puzzle_solved_text)
                )
            }
            PuzzleDescriptionType.Fail -> {
                TextWithIcon(
                    icon = painterResource(R.drawable.fail),
                    text = stringResource(R.string.wrong_text)
                )
            }
            PuzzleDescriptionType.WhiteMove -> {
                TextWithColor(
                    color = Color.White,
                    text = stringResource(R.string.white_player_turn_text)
                )
            }
        }
    }
}

@Composable
private fun TextWithIcon(
    icon: Painter,
    text: String
) {
    Icon(
        painter = icon,
        contentDescription = "icon",
        tint = Color.Unspecified
    )
    Spacer(Modifier.width(8.dp))
    Text(
        text = text,
        color = Color.Black
    )
}

@Composable
private fun TextWithColor(
    color: Color,
    text: String
) {
    ColorSquare(
        color = color
    )
    Spacer(Modifier.width(8.dp))
    Text(
        text = text,
        color = Color.Black
    )
}