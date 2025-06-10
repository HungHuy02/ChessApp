package com.huy.chess.ui.playonline

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.NotationPane
import com.huy.chess.ui.component.PlayerArea
import com.huy.chess.ui.component.getChessPiecePainters
import com.huy.chess.ui.playonline.composables.PlayOnlineScreenBottomBar
import com.huy.chess.utils.enums.GameResult
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
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val density = LocalDensity.current.density
        val boardSize = (configuration.screenWidthDp * density).toInt()
        val cellSize = boardSize / 22
        val list = remember { getChessPiecePainters(context, cellSize) }
        val (topBar, pane, playerTop, board, playerBottom, bar) = createRefs()
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

        PlayerArea(
            name = state.player2.name,
            avatar = state.player2.avatar ?: "",
            map = state.capturedPiece,
            side = !state.side,
            list = list,
            modifier = Modifier.constrainAs(playerTop) {
                top.linkTo(pane.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                bottom.linkTo(board.top)
            }
        )

        ChessBoard(
            side = state.side,
            onCapture = { onAction(PlayOnlineAction.PieceCaptured(it)) },
            onMove = {move, moveMaterial -> onAction(PlayOnlineAction.OnMove(move, moveMaterial)) },
            onResult = { result, whiteSide -> onAction(PlayOnlineAction.Result(result, whiteSide)) },
            opponentMove = state.nextMove,
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        PlayerArea(
            name = state.player1.name,
            avatar = state.player1.avatar ?: "",
            map = state.capturedPiece,
            side = state.side,
            list = list,
            modifier = Modifier.constrainAs(playerBottom) {
                top.linkTo(board.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                bottom.linkTo(bar.top)
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

