package com.huy.chess.ui.component

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.model.Piece
import com.huy.chess.utils.Utils

private fun getPieceDrawableId(piece: Char): Int {
    return when (piece) {
        'p' -> 0
        'r' -> 1
        'b' -> 2
        'n' -> 3
        'q' -> 4
        'k' -> 5
        'P' -> 6
        'R' -> 7
        'B' -> 8
        'N' -> 9
        'Q' -> 10
        else -> 11
    }
}

fun getChessPiecePainters(context: Context, size: Int): List<ImageBitmap> {
    val pieceResIds = listOf(
        R.drawable.bpawn, R.drawable.brook, R.drawable.bbishop, R.drawable.bknight,
        R.drawable.bqueen, R.drawable.bking, R.drawable.wpawn, R.drawable.wrook,
        R.drawable.wbishop, R.drawable.wknight, R.drawable.wqueen, R.drawable.wking,
    )

    return pieceResIds.map { resId ->
        Utils.loadImageBimap(context, resId, size)
    }
}

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val boardSize = (configuration.screenWidthDp * density).toInt()
    val cellSize = boardSize / 8
    val boardBitmap = remember { Utils.loadImageBimap(context, R.drawable.chess_board, boardSize) }
    val list = remember { getChessPiecePainters(context, cellSize) }
    val board = remember { Utils.initBoard() }

    var selectedPiece: Piece? by remember { mutableStateOf(null) }
    var desSpot: Piece? by remember { mutableStateOf(null) }

    val pieceOffset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    LaunchedEffect(desSpot) {
        if (selectedPiece != null && desSpot != null) {
            val startX = selectedPiece!!.y * cellSize.toFloat()
            val startY = selectedPiece!!.x * cellSize.toFloat()
            val endX = desSpot!!.y * cellSize.toFloat()
            val endY = desSpot!!.x * cellSize.toFloat()

            pieceOffset.snapTo(Offset(startX, startY))
            pieceOffset.animateTo(Offset(endX, endY), animationSpec = tween(300))

            board[desSpot!!.x][desSpot!!.y] = Piece(desSpot!!.x, desSpot!!.y, selectedPiece!!.piece)
            board[selectedPiece!!.x][selectedPiece!!.y] = Piece(selectedPiece!!.x, selectedPiece!!.y, ' ')

            selectedPiece = null
            desSpot = null
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(configuration.screenWidthDp.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val row = (offset.y / cellSize).toInt()
                    val col = (offset.x / cellSize).toInt()
                    if (row in 0..7 && col in 0..7) {
                        if (selectedPiece == null || (selectedPiece?.piece?.isUpperCase() == board[row][col].piece.isUpperCase())) {
                            selectedPiece = board[row][col]
                        } else {
                            desSpot = board[row][col]
                        }
                    }
                }
            }
    ) {
        val cellSize = size.width / 8
        drawImage(
            image = boardBitmap,
            topLeft = Offset(0f, 0f),
        )
        selectedPiece?.let {
            drawRect(
                color = Color.Yellow.copy(alpha = 0.5f),
                topLeft = Offset(it.y * cellSize, it.x * cellSize),
                size = Size(cellSize, cellSize)
            )
        }

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val piece = board[row][col]
                if (piece.piece != ' ') {
                    val isMovingPiece = selectedPiece == piece && desSpot != null
                    val offset = if (isMovingPiece) pieceOffset.value else Offset(col * cellSize, row * cellSize)

                    drawImage(
                        image = list[getPieceDrawableId(piece.piece)],
                        topLeft = offset
                    )
                }
            }
        }
    }
}

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    list: List<ImageBitmap>,
    fen: String
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val boardSizeDp = configuration.screenWidthDp / 3
    val boardSize = (boardSizeDp * density).toInt()
    val boardBitmap = remember { Utils.loadImageBimap(context, R.drawable.chess_board, boardSize) }
    val rows = fen.split(" ")[0].split("/")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(boardSizeDp.dp + 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Canvas(
            modifier = Modifier
                .size(boardSizeDp.dp)
        ) {
            val cellSize = size.width / 8
            drawImage(
                image = boardBitmap,
                topLeft = Offset(0f, 0f),
            )

            var rowIndex = 0
            rows.forEach { rowString ->
                var colIndex = 0
                rowString.forEach { char ->
                    if (char.isDigit()) {
                        colIndex += (char - '0')
                    } else {
                        drawImage(
                            image = list[getPieceDrawableId(char)],
                            topLeft = Offset(colIndex * cellSize, rowIndex * cellSize),
                        )
                        colIndex++
                    }
                }
                rowIndex++
            }
        }
    }
}


