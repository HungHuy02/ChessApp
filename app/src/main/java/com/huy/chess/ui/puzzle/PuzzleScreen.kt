package com.huy.chess.ui.puzzle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.puzzle.composable.PuzzleButton
import com.huy.chess.viewmodel.PuzzleViewModel

@Composable
fun PuzzleScreen(
    viewModel: PuzzleViewModel = hiltViewModel(),
    navigateSolvePuzzles: () -> Unit,
    navigateDailyPuzzles: () -> Unit,
    navigateSetupPuzzleRush: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PuzzleEffect.NavigateDailyPuzzles -> navigateDailyPuzzles()
                PuzzleEffect.NavigateSetupPuzzleRush -> navigateSetupPuzzleRush()
                PuzzleEffect.NavigateSolvePuzzles -> navigateSolvePuzzles()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (PuzzleAction) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.standardboard),
            contentDescription = "board",
            contentScale = ContentScale.FillWidth,
            alpha = 0.6f,
            modifier = Modifier.fillMaxWidth()
        )
        Column (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        ) {
            AppButton(
                onClick = { onAction(PuzzleAction.ClickedSolvePuzzles) },
                text = stringResource(R.string.solve_puzzles_text),
                iconPosition = IconPosition.NONE,
                modifier = Modifier.fillMaxWidth()
            )
            PuzzleButton(
                text = stringResource(R.string.puzzle_rush_text),
                painter = painterResource(R.drawable.rush)
            ) {
                onAction(PuzzleAction.ClickedPuzzleRush)
            }
            PuzzleButton(
                text = stringResource(R.string.daily_puzzle_text),
                painter = painterResource(R.drawable.dailypuzzle)
            ) {
                onAction(PuzzleAction.ClickedDailyPuzzle)
            }
        }
    }
}