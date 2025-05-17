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
import com.huy.chess.ui.play.composables.PlayScreenBottomBar
import com.huy.chess.ui.play.composables.PlayerArea
import com.huy.chess.ui.play.composables.Timer
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.viewmodel.PlayViewModel

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel(),
    showPlayOptionsDialog: () -> Unit,
    showEndGameDialog: (GameResult) -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PlayEffect.PopBackStack -> popBackStack()
                PlayEffect.ShowPlayOptionsDialog -> showPlayOptionsDialog()
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
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, pane, board, playerTop, playerBottom, bar) = createRefs()
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

        PlayerArea(
            name = "Test",
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
            autoRotate = false,
            onCapture = { onAction(PlayAction.PieceCaptured(it)) },
            onMove = { onAction(PlayAction.Move(it)) },
            onResult = { result, whiteSide -> onAction(PlayAction.Result(result, whiteSide)) },
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        PlayerArea(
            name = "Test",
            map = state.capturedPiece,
            side = state.bottomSide,
            list = list,
            modifier = Modifier.constrainAs(playerBottom) {
                top.linkTo(board.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                bottom.linkTo(bar.top)
            }
        )

//        Timer(
//            time = 0,
//            isWhite = true,
//            modifier = Modifier.constrainAs(timerTop) {
//                top.linkTo(board.bottom, margin = 10.dp)
//                end.linkTo(parent.end)
//            }
//        )
//
//        Timer(
//            time = 0,
//            isWhite = false,
//            modifier = Modifier.constrainAs(timerBottom) {
//                top.linkTo(timerTop.bottom, margin = 10.dp)
//                end.linkTo(timerTop.end)
//            }
//        )

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

