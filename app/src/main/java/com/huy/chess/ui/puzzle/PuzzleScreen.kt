package com.huy.chess.ui.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.getChessPiecePainters

@Composable
fun PuzzleScreen() {
    val context = LocalContext.current
    val list = remember { getChessPiecePainters(context) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ChessBoard(list = list)
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