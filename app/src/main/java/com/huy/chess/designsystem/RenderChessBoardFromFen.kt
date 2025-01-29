package com.huy.chess.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@Composable
fun RenderChessBoardFromFEN(fen: String, size: Dp) {
    val rows = fen.split(" ")[0].split("/")
    Box(modifier = Modifier.size(size)) {
        Image(painter = painterResource(R.drawable.chess_board), contentDescription = null)
        Column {
            rows.map {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    it.forEach {
                        if(!it.isDigit()) {
                            Box(
                                modifier = Modifier.fillMaxHeight().weight(1f)
                            ) {
                                Image(
                                    painter = painterResource(
                                        when(it) {
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
                                    contentScale = ContentScale.Inside
                                )
                            }
                        } else {
                            for ( i in 1..it.toString().toInt()) {
                                Box(modifier = Modifier.fillMaxHeight().weight(1f))
                            }
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
    RenderChessBoardFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 240.dp)
}

