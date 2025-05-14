package com.huy.chess.ui.playonline

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
import com.huy.chess.R
import com.huy.chess.ui.component.CapturedPiece
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.NotationPane
import com.huy.chess.ui.play.composables.Timer
import com.huy.chess.ui.playbot.composables.PlayBotScreenBottomBar
import com.huy.chess.ui.playonline.composables.PlayOnlineScreenBottomBar
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.viewmodel.PlayBotViewModel
import com.huy.chess.viewmodel.PlayOnlineViewModel

@Composable
fun PlayOnlineScreen(
    viewModel: PlayOnlineViewModel = hiltViewModel(),
    showPlayOptionsDialog: () -> Unit,
    showEndGameDialog: (GameResult) -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PlayOnlineEffect.PopBackStack -> popBackStack()
                PlayOnlineEffect.ShowPlayOptionsDialog -> showPlayOptionsDialog()
                is PlayOnlineEffect.ShowEndGameDialog -> showEndGameDialog(it.gameResult)
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: PlayOnlineState,
    onAction: (PlayOnlineAction) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, pane, board, capture, timerTop, timerBottom, bar) = createRefs()
        ChessTopAppBar(
            title = stringResource(R.string.app_name),
            onClickBack = { onAction(PlayOnlineAction.ClickedBackButton) },
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
            onCapture = { onAction(PlayOnlineAction.PieceCaptured(it)) },
            onMove = {move, fen -> onAction(PlayOnlineAction.Move(move, fen)) },
            onResult = { result, whiteSide -> onAction(PlayOnlineAction.Result(result, whiteSide)) },
            nextMove = state.nextMove,
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

        PlayOnlineScreenBottomBar (
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

