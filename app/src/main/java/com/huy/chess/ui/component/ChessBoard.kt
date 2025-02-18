package com.huy.chess.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.model.Piece

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
                    Box(
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
                                        when (it) {
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

                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f)
                                )
                            } else {
                                Box(
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
    var board by remember {
        mutableStateOf(
            arrayOf(
                arrayOf(
                    Piece(0, 0, 'r'), Piece(0, 1, 'n'), Piece(0, 2, 'b'), Piece(0, 3, 'q'),
                    Piece(0, 4, 'k'), Piece(0, 5, 'b'), Piece(0, 6, 'n'), Piece(0, 7, 'r')
                ),
                arrayOf(
                    Piece(1, 0, 'p'), Piece(1, 1, 'p'), Piece(1, 2, 'p'), Piece(1, 3, 'p'),
                    Piece(1, 4, 'p'), Piece(1, 5, 'p'), Piece(1, 6, 'p'), Piece(1, 7, 'p')
                ),
                arrayOf(
                    Piece(2, 0, ' '), Piece(2, 1, ' '), Piece(2, 2, ' '), Piece(2, 3, ' '),
                    Piece(2, 4, ' '), Piece(2, 5, ' '), Piece(2, 6, ' '), Piece(2, 7, ' ')
                ),
                arrayOf(
                    Piece(3, 0, ' '), Piece(3, 1, ' '), Piece(3, 2, ' '), Piece(3, 3, ' '),
                    Piece(3, 4, ' '), Piece(3, 5, ' '), Piece(3, 6, ' '), Piece(3, 7, ' ')
                ),
                arrayOf(
                    Piece(4, 0, ' '), Piece(4, 1, ' '), Piece(4, 2, ' '), Piece(4, 3, ' '),
                    Piece(4, 4, ' '), Piece(4, 5, ' '), Piece(4, 6, ' '), Piece(4, 7, ' ')
                ),
                arrayOf(
                    Piece(5, 0, ' '), Piece(5, 1, ' '), Piece(5, 2, ' '), Piece(5, 3, ' '),
                    Piece(5, 4, ' '), Piece(5, 5, ' '), Piece(5, 6, ' '), Piece(5, 7, ' ')
                ),
                arrayOf(
                    Piece(6, 0, 'P'), Piece(6, 1, 'P'), Piece(6, 2, 'P'), Piece(6, 3, 'P'),
                    Piece(6, 4, 'P'), Piece(6, 5, 'P'), Piece(6, 6, 'P'), Piece(6, 7, 'P')
                ),
                arrayOf(
                    Piece(7, 0, 'R'), Piece(7, 1, 'N'), Piece(7, 2, 'B'), Piece(7, 3, 'Q'),
                    Piece(7, 4, 'K'), Piece(7, 5, 'B'), Piece(7, 6, 'N'), Piece(7, 7, 'R')
                )
            )
        )
    }
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
                        if (it.piece != ' ') {
                            Image(
                                painter = painterResource(
                                    when (it.piece) {
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

                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
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



