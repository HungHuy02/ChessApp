package com.huy.chess.ui.component

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.model.Piece
import com.huy.chess.utils.Utils

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    fen: String,
    size: Dp
) {
    val rows = fen.split(" ")[0].split("/")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size + 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Image(
            painter = painterResource(R.drawable.chess_board),
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(MaterialTheme.shapes.small)
        )
        Column(
            modifier = Modifier.size(size)
        ) {
            rows.forEach { string ->
                if (string == "8") {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        string.forEach {
                            if (!it.isDigit()) {
                                Image(
                                    painter = painterResource(
                                        getPieceDrawableId(it)
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f)
                                )
                            } else {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight((it - '0').toFloat())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier
) {
    val size = LocalConfiguration.current.screenWidthDp
    var selectedPiece: Piece? by remember { mutableStateOf(null) }
    val board = Utils.initBoard()
    val spotSize = (size / 8).dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.chess_board),
            contentDescription = null,
            modifier = Modifier
                .size(size.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            board.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    row.forEach {
                        var offset by remember{ mutableStateOf(DpOffset.Zero) }
                        val animateOffset by animateOffsetAsState(targetValue = Offset(offset.x.value, offset.y.value), label = "offset")
                        if (it.piece != ' ') {
                            Image(
                                painter = painterResource(
                                    getPieceDrawableId(it.piece)
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .offset(animateOffset.x.dp, animateOffset.y.dp)
                                    .background(if (it == selectedPiece) Color.DarkGray else Color.Transparent)
                                    .clickable {
                                        if (selectedPiece == null || selectedPiece != it) selectedPiece =
                                            it
                                        offset = DpOffset(offset.x + spotSize, offset.y - spotSize * 2)
                                    }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getPieceDrawableId(piece: Char): Int {
    return when (piece) {
        'p' -> R.drawable.bpawn
        'r' -> R.drawable.brook
        'b' -> R.drawable.bbishop
        'n' -> R.drawable.bknight
        'q' -> R.drawable.bqueen
        'k' -> R.drawable.bknight
        'P' -> R.drawable.wpawn
        'R' -> R.drawable.wrook
        'B' -> R.drawable.wbishop
        'N' -> R.drawable.wknight
        'Q' -> R.drawable.wqueen
        else -> R.drawable.wking
    }
}

@Preview
@Composable
private fun Preview() {
    ChessBoard(
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PP2PPPP/RNBQKBNR w KQkq - 0 1",
        size = 240.dp
    )
}

@Preview
@Composable
private fun PreviewMainChess() {
    ChessBoard()
}



