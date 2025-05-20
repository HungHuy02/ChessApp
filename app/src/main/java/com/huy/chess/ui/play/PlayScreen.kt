package com.huy.chess.ui.play

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
import com.huy.chess.ui.component.getChessPiecePainters
import com.huy.chess.ui.play.composables.OptionsDialog
import com.huy.chess.ui.play.composables.PlayScreenBottomBar
import com.huy.chess.ui.component.PlayerArea
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.viewmodel.PlayViewModel

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel(),
    showEndGameDialog: (GameResult) -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PlayEffect.PopBackStack -> popBackStack()
                is PlayEffect.ShowEndGameDialog -> showEndGameDialog(it.gameResult)
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
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val boardSize = (configuration.screenWidthDp * density).toInt()
    val cellSize = boardSize / 22
    val list = remember { getChessPiecePainters(context, cellSize) }
    if(state.showDialog)
        OptionsDialog(
            onDismiss = { onAction(PlayAction.CloseDialog) },
            enableRotate = state.autoRotate,
            onClick = onAction
        )
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, pane, board, playerTop, playerBottom, bar) = createRefs()
        ChessTopAppBar(
            title = stringResource(R.string.pass_and_play_text),
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
            currentNotation = state.currentFen - 1,
            onClick = { onAction(PlayAction.ClickedNotation(it)) },
            modifier = Modifier.constrainAs(pane) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        PlayerArea(
            name = state.topName,
            avatar = state.topAvatar,
            map = state.capturedPiece,
            side = !state.bottomSide,
            list = list,
            modifier = Modifier.constrainAs(playerTop) {
                top.linkTo(pane.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                bottom.linkTo(board.top)
            }
        )

        ChessBoard(
            autoRotate = state.autoRotate,
            onCapture = { onAction(PlayAction.PieceCaptured(it)) },
            onMove = {move, fen -> onAction(PlayAction.Move(move, fen)) },
            onResult = { result, whiteSide -> onAction(PlayAction.Result(result, whiteSide)) },
            fen = state.displayFen,
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        PlayerArea(
            name = state.bottomName,
            avatar = state.bottomAvatar,
            map = state.capturedPiece,
            side = state.bottomSide,
            list = list,
            modifier = Modifier.constrainAs(playerBottom) {
                top.linkTo(board.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                bottom.linkTo(bar.top)
            }
        )

        PlayScreenBottomBar(
            onClick = onAction,
            isEnd = state.isEnd,
            modifier = Modifier.constrainAs(bar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

