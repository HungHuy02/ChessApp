package com.huy.chess.ui.play

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.play.composables.NotationPane
import com.huy.chess.ui.play.composables.PlayScreenBottomBar
import com.huy.chess.ui.play.composables.Timer
import com.huy.chess.viewmodel.PlayViewModel
import com.huy.chess.R
import com.huy.chess.ui.play.composables.CapturedPiece

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel(),
    showPlayOptionsDialog: () -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PlayEffect.PopBackStack -> popBackStack()
                PlayEffect.ShowPlayOptionsDialog -> showPlayOptionsDialog()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: PlayState,
    onAction: (PlayAction) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, pane, board, capture, timerTop, timerBottom, bar) = createRefs()
        ChessTopAppBar(
            title = stringResource(R.string.app_name),
            onClickBack = { onAction(PlayAction.ClickedBackButton) },
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        NotationPane(
            notations = state.notationList,
            modifier = Modifier.constrainAs(pane) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        ChessBoard(
            onCapture = { onAction(PlayAction.PieceCaptured(it)) },
            onMove = { onAction(PlayAction.Move(it)) },
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        CapturedPiece(
            map = state.capturedPiece,
            modifier = Modifier
                .constrainAs(capture) {
                    top.linkTo(board.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        Timer(
            time = 0,
            isWhite = true,
            modifier = Modifier.constrainAs(timerTop) {
                top.linkTo(board.bottom, margin = 10.dp)
                end.linkTo(parent.end)
            }
        )

        Timer(
            time = 0,
            isWhite = false,
            modifier = Modifier.constrainAs(timerBottom) {
                top.linkTo(timerTop.bottom, margin = 10.dp)
                end.linkTo(timerTop.end)
            }
        )

        PlayScreenBottomBar(
            onClick = onAction,
            modifier = Modifier.constrainAs(bar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

