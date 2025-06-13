package com.huy.chess.ui.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
import com.huy.chess.ui.history.composables.HistoryScreenBottomBar
import com.huy.chess.ui.history.composables.OptionsDialog
import com.huy.chess.utils.Utils
import com.huy.chess.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                HistoryEffect.PopBackStack -> popBackStack()
                is HistoryEffect.ShowEndGameDialog -> {}
                is HistoryEffect.CopyToClipBoard -> {
                    Utils.copyToClipboard(context = context, text = it.pgn)
                }
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val boardSize = (configuration.screenWidthDp * density).toInt()
    val cellSize = boardSize / 22
    val list = remember { getChessPiecePainters(context, cellSize) }
    if(state.showDialog)
        OptionsDialog (
            onDismiss = { onAction(HistoryAction.CloseDialog) },
            enableRotate = true,
            onClick = onAction
        )
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, pane, board, playerTop, playerBottom, bar) = createRefs()
        ChessTopAppBar(
            icon = painterResource(R.drawable.logo),
            onClickBack = { onAction(HistoryAction.ClickedBackButton) },
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
            onClick = { onAction(HistoryAction.ClickedNotation(it)) },
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
            fen = state.fen,
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        PlayerArea(
            name = state.bottomName,
            avatar = state.bottomAvatar,
            side = state.bottomSide,
            list = list,
            modifier = Modifier.constrainAs(playerBottom) {
                top.linkTo(board.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                bottom.linkTo(bar.top)
            }
        )

        HistoryScreenBottomBar (
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