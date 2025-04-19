package com.huy.chess.ui.dailypuzzle

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.dailypuzzle.composable.DailyPuzzleBottomBar
import com.huy.chess.ui.dailypuzzle.composable.DateChange
import com.huy.chess.ui.dailypuzzle.composable.DescriptionText
import com.huy.chess.viewmodel.DailyPuzzleViewModel

@Composable
fun DailyPuzzleScreen(
    viewModel: DailyPuzzleViewModel = hiltViewModel(),
    navigateSelectDate: () -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                DailyPuzzleEffect.NavigateSelectDate -> navigateSelectDate()
                DailyPuzzleEffect.PopBackStack -> popBackStack()
                DailyPuzzleEffect.ShowSuccessDialog -> {}
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: DailyPuzzleState,
    onAction: (DailyPuzzleAction) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, pane, board, description, bar) = createRefs()
        ChessTopAppBar(
            title = "test",
            action = { onAction(DailyPuzzleAction.ClickedBackButton) },
            onClickBack = { onAction(DailyPuzzleAction.ClickedBackButton) },
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        DateChange(
            date = state.date,
            onAction = { onAction(DailyPuzzleAction.ClickedDate) },
            modifier = Modifier.constrainAs(pane) {
                top.linkTo(topBar.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        ChessBoard(
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        DescriptionText(
            puzzleStatus = state.puzzleStatus,
            move = "e4",
            modifier = Modifier.constrainAs(description) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bar.top, margin = 8.dp)
            }
        )

        DailyPuzzleBottomBar (
            puzzleStatus = state.puzzleStatus,
            onClick = {},
            modifier = Modifier.constrainAs(bar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}