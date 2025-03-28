package com.huy.chess.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.getChessPiecePainters
import com.huy.chess.ui.home.composables.HomeItems
import com.huy.chess.utils.Constants

@Composable
fun HomeScreen(
    navigateToPlayScreen: () -> Unit
) {
    val context = LocalContext.current
    val list = remember { getChessPiecePainters(context) }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.weight(1f)
        ) {
            item {
                HomeItems(
                    fen = Constants.START_FEN,
                    list = list,
                    title = stringResource(R.string.play_online_text),
                    description = stringResource(R.string.play_online_dec_text),
                    icon = R.drawable.playwhite_cea685ba
                )
            }

            item {
                HomeItems(
                    fen = Constants.START_FEN,
                    list = list,
                    title = stringResource(R.string.solve_puzzles_text),
                    description = stringResource(R.string.solve_puzzles_dec_text),
                    icon = R.drawable.puzzles
                )
            }

            item {
                HomeItems(
                    fen = Constants.START_FEN,
                    list = list,
                    title = stringResource(R.string.daily_puzzle_text),
                    description = stringResource(R.string.daily_puzzles_dec_text),
                    icon = R.drawable.dailypuzzle
                )
            }

            item {
                HomeItems(
                    fen = Constants.START_FEN,
                    list = list,
                    title = stringResource(R.string.play_with_bot_text),
                    description = stringResource(R.string.play_with_bot_dec_text),
                    icon = R.drawable.stockfish
                )
            }

            item {
                HomeItems(
                    fen = Constants.START_FEN,
                    list = list,
                    title = stringResource(R.string.study_text),
                    description = stringResource(R.string.study_dec_text),
                    icon = R.drawable.lessons
                )
            }
        }

        AppButton(
            onClick = navigateToPlayScreen,
            text = stringResource(R.string.play_text),
            textColor = Color.White,
            iconPosition = IconPosition.NONE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

