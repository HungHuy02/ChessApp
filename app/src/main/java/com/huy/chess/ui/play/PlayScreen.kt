package com.huy.chess.ui.play

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.huy.chess.ui.component.ChessBoard
import com.huy.chess.ui.play.composables.NotationPane
import com.huy.chess.ui.play.composables.PlayScreenBottomBar
import com.huy.chess.ui.play.composables.Timer

@Composable
fun PlayScreen() {
    val size = LocalConfiguration.current.screenWidthDp
    var list by remember { mutableStateOf(listOf("")) }
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (pane, board, timerTop, timerBottom, bar) = createRefs()
        NotationPane(
            notations = list,
            modifier = Modifier.constrainAs(pane) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        ChessBoard(
            modifier = Modifier
                .constrainAs(board) {
                    top.linkTo(parent.top, margin = (-20).dp)
                    bottom.linkTo(parent.bottom)
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
            modifier = Modifier.constrainAs(bar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview
@Composable
fun Preview() {
    PlayScreen()
}

