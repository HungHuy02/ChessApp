package com.huy.chess.ui.solvepuzzles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.solvepuzzles.composable.PuzzleBottomBar
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescription
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescriptionType
import com.huy.chess.viewmodel.SolvePuzzlesViewModel

@Composable
fun SolvePuzzlesScreen(
    viewModel: SolvePuzzlesViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SolvePuzzlesEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: SolvePuzzlesState,
    onAction: (SolvePuzzlesAction) -> Unit
) {
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, des, board, bottomBar) = createRefs()
        ChessTopAppBar(
            onClickSettings = {},
            onClickBack = { onAction(SolvePuzzlesAction.ClickedBack) },
            title = stringResource(R.string.puzzle_text),
            icon = painterResource(R.drawable.puzzles),
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        PuzzleDescription(
            type = PuzzleDescriptionType.Correct,
            modifier = Modifier.constrainAs(des) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(board.top, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        ChessBoard(
            modifier = Modifier.constrainAs(board) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        PuzzleBottomBar(
            modifier = Modifier.constrainAs(bottomBar) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}