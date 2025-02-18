package com.huy.chess.ui.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.IconPosition

@Composable
fun PuzzleScreen() {
    BaseScreen(
        title = stringResource(R.string.puzzle_text)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ChessBoard()
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                AppButton(
                    onClick = {},
                    text = stringResource(R.string.solve_puzzles_text),
                    iconPosition = IconPosition.NONE,
                    modifier = Modifier.fillMaxWidth()
                )
                AppButton(
                    onClick = {},
                    text = stringResource(R.string.puzzle_rush_text),
                    iconPosition = IconPosition.NEXT_TO_TEXT,
                    painter = painterResource(R.drawable.puzzles),
                    modifier = Modifier.fillMaxWidth()
                )
                AppButton(
                    onClick = {},
                    text = stringResource(R.string.daily_puzzle_text),
                    iconPosition = IconPosition.NEXT_TO_TEXT,
                    painter = painterResource(R.drawable.dailypuzzle),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PuzzleScreen()
}