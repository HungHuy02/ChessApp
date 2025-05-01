package com.huy.chess.ui.component

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.data.model.MoveResult
import com.huy.chess.data.model.Piece
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

fun getChessPieceBitmap(context: Context): List<Bitmap> {
    val pieceResIds = listOf(
        R.drawable.bpawn, R.drawable.brook, R.drawable.bbishop, R.drawable.bknight,
        R.drawable.bqueen, R.drawable.bking, R.drawable.wpawn, R.drawable.wrook,
        R.drawable.wbishop, R.drawable.wknight, R.drawable.wqueen, R.drawable.wking,
    )

    return pieceResIds.map { resId ->
        Utils.loadBitmap(context, resId)
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

    var whiteSide: Boolean by remember { mutableStateOf(true) }
    var isGameEnd: Boolean by remember { mutableStateOf(false) }
    var isPromoting: Boolean by remember { mutableStateOf(false) }
    var movedSpot : List<Piece> by remember { mutableStateOf(emptyList()) }
    var specificPieceMoves : List<Int> by remember { mutableStateOf(emptyList())  }
    var selectedPiece: Piece? by remember { mutableStateOf(null) }
    var desSpot: Piece? by remember { mutableStateOf(null) }
    val pieceOffset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    var isMoving by remember { mutableStateOf(false) }
    var promotionPair by remember { mutableStateOf(0 to 0) }

    if(isPromoting) {
        LaunchedEffect(selectedPiece) {
            Log.e("tag", "test")
            if(selectedPiece != null) {
                if(selectedPiece!!.y == (promotionPair.second % 8)) {
                    if(whiteSide) {
                        if(selectedPiece!!.x in 0..3) {
                            val target = when(selectedPiece!!.x) {
                                0 -> 'Q'
                                1 -> 'N'
                                2 -> 'R'
                                3 -> 'B'
                                else -> ' '
                            }
                            val x = promotionPair.second / 8
                            val y = promotionPair.second % 8
                            makeMove(promotionPair.first, 'P', promotionPair.second, ' ', target)
                            board[x][y] = Piece(x, y, target)

//                        movedSpot = listOf(selectedPiece!!, desSpot!!)
                            whiteSide = !whiteSide
                        } else {
                            val x = promotionPair.first / 8
                            val y = promotionPair.first % 8
                            board[x][y] = Piece(x, y, 'p')
                        }
                    }else {
                        if(selectedPiece!!.x in 4..7) {
                            val target = when(selectedPiece!!.x) {
                                4 -> 'b'
                                5 -> 'r'
                                6 -> 'n'
                                7 -> 'q'
                                else -> ' '
                            }
                            val x = promotionPair.second / 8
                            val y = promotionPair.second % 8
                            makeMove(promotionPair.first, 'p', promotionPair.second,' ', target)
                            board[x][y] = Piece(x, y, target)

//                        movedSpot = listOf(selectedPiece!!, desSpot!!)
                            whiteSide = !whiteSide
                        } else {
                            val x = promotionPair.first / 8
                            val y = promotionPair.first % 8
                            board[x][y] = Piece(x, y, 'p')
                        }
                    }
                } else {
                    val x = promotionPair.first / 8
                    val y = promotionPair.first % 8
                    board[x][y] = Piece(x, y, if(whiteSide) 'P' else 'p')
                }
                selectedPiece = null
                isPromoting = false
                promotionPair = 0 to 0
            }
        }
    }

    LaunchedEffect(desSpot) {
        if (selectedPiece != null && desSpot != null) {
            val startX = selectedPiece!!.y * cellSize.toFloat()
            val startY = selectedPiece!!.x * cellSize.toFloat()
            val endX = desSpot!!.y * cellSize.toFloat()
            val endY = desSpot!!.x * cellSize.toFloat()

            specificPieceMoves = emptyList()

            val result = makeMove(selectedPiece!!.x * 8 + selectedPiece!!.y, selectedPiece!!.piece, desSpot!!.x * 8 + desSpot!!.y, desSpot!!.piece, ' ')
            Log.e("tag", result.diffMove.toString())
            if (result.diffMove == 0) {
                isMoving = true
                pieceOffset.snapTo(Offset(startX, startY))
                pieceOffset.animateTo(Offset(endX, endY), animationSpec = tween(300))
                promotionPair = selectedPiece!!.x * 8 + selectedPiece!!.y to desSpot!!.x * 8 + desSpot!!.y
                board[selectedPiece!!.x][selectedPiece!!.y] = Piece(selectedPiece!!.x, selectedPiece!!.y, ' ')
                isMoving = false
                isPromoting = true
            } else {
                if(result.diffMove != -1) {
                    isMoving = true
                    pieceOffset.snapTo(Offset(startX, startY))
                    pieceOffset.animateTo(Offset(endX, endY), animationSpec = tween(300))
                    if(result.diffMove != 65) {
                        Log.e("tag", "source ${result.diffMove and 0x3f}")
                        Log.e("tag", "taget ${(result.diffMove and 0xfc0) shr 6}")
                        val source = result.diffMove and 0x3f
                        val sourceX = source / 8
                        val sourceY = source % 8
                        val target = (result.diffMove and 0xfc0) shr 6
                        val targetX = target / 8
                        val targetY = target % 8
                        board[targetX][targetY] = Piece(targetX, targetY, board[sourceX][sourceY].piece)
                        board[sourceX][sourceY] = Piece(sourceX, sourceY, ' ')
                    }
                    isMoving = false

                    board[desSpot!!.x][desSpot!!.y] = Piece(desSpot!!.x, desSpot!!.y, selectedPiece!!.piece)
                    board[selectedPiece!!.x][selectedPiece!!.y] = Piece(selectedPiece!!.x, selectedPiece!!.y, ' ')

                    movedSpot = listOf(selectedPiece!!, desSpot!!)
                    whiteSide = !whiteSide
                    if(!hasOneLegalMove()) isGameEnd = true
                }
            }
            selectedPiece = null
            desSpot = null
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(configuration.screenWidthDp.dp)
            .then(
                if (!isGameEnd)
                    Modifier.pointerInput(Unit) {
                        detectTapGestures { offset ->
                            val row = (offset.y / cellSize).toInt()
                            val col = (offset.x / cellSize).toInt()
                            if (row in 0..7 && col in 0..7) {
                                if(isPromoting) {
                                    selectedPiece = Piece(row, col, ' ')
                                } else if ((selectedPiece == null && board[row][col].piece.isUpperCase() == whiteSide) || (!board[row][col].piece.isWhitespace() && selectedPiece?.piece?.isUpperCase() == board[row][col].piece.isUpperCase())) {
                                    selectedPiece = board[row][col]
                                    specificPieceMoves =
                                        getLegalMoves((row * 8 + col) or (pieceToInt(selectedPiece!!.piece) shl 6)).map { (it and 0xfc0) shr 6 }
                                } else {
                                    if (selectedPiece != null)
                                        desSpot = board[row][col]
                                }
                            }
                        }
                    }
                else Modifier
            )
    ) {
        val cellSize = size.width / 8
        drawImage(
            image = boardBitmap,
            topLeft = Offset(0f, 0f)
        )
        selectedPiece?.let {
            drawRect(
                color = Color.Yellow.copy(alpha = 0.5f),
                topLeft = Offset(it.y * cellSize, it.x * cellSize),
                size = Size(cellSize, cellSize)
            )
        }
        for(spot in movedSpot) {
            drawRect(
                color = Color.Yellow.copy(alpha = 0.5f),
                topLeft = Offset(spot.y * cellSize, spot.x * cellSize),
                size = Size(cellSize, cellSize)
            )
        }

        for(square in specificPieceMoves) {
            val col = square % 8
            val row = square / 8

            val centerX = col * cellSize + cellSize / 2
            val centerY = row * cellSize + cellSize / 2

            if (board[row][col].piece == ' ') {
                drawCircle(
                    color = Color.White,
                    radius = cellSize / 6,
                    center = Offset(centerX, centerY)
                )
            } else {
                drawCircle(
                    color = Color.White,
                    radius = cellSize / 2.5f,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = cellSize / 12)
                )
            }
        }

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val piece = board[row][col]
                if (piece.piece != ' ') {
                    val isMovingPiece = selectedPiece == piece && isMoving
                    val offset = if (isMovingPiece) pieceOffset.value else Offset(col * cellSize, row * cellSize)

                    drawImage(
                        image = list[getPieceDrawableId(piece.piece)],
                        topLeft = offset
                    )
                }
            }
        }
        if(isPromoting) {
            val x = promotionPair.second % 8
            val y = promotionPair.second / 8
            if(whiteSide) {
                drawRect(
                    color = Color.White,
                    topLeft = Offset(x * cellSize, y * cellSize),
                    size = Size(cellSize, cellSize * 4)
                )
                drawImage(
                    image = list[10],
                    topLeft = Offset(x * cellSize, y * cellSize),
                )
                drawImage(
                    image = list[9],
                    topLeft = Offset(x * cellSize, (y + 1) * cellSize),
                )
                drawImage(
                    image = list[7],
                    topLeft = Offset(x * cellSize, (y + 2) * cellSize),
                )
                drawImage(
                    image = list[8],
                    topLeft = Offset(x * cellSize, (y + 3) * cellSize),
                )
            } else {
                val rY = (y * cellSize) - cellSize * 3
                drawRect(
                    color = Color.White,
                    topLeft = Offset(x * cellSize, rY),
                    size = Size(cellSize, cellSize * 4)
                )
                drawImage(
                    image = list[2],
                    topLeft = Offset(x * cellSize, rY),
                )
                drawImage(
                    image = list[1],
                    topLeft = Offset(x * cellSize, rY + cellSize),
                )
                drawImage(
                    image = list[3],
                    topLeft = Offset(x * cellSize, rY + cellSize * 2),
                )
                drawImage(
                    image = list[4],
                    topLeft = Offset(x * cellSize, rY + cellSize * 3),
                )
            }
        }
    }
}

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    list: List<Bitmap>,
    fen: String
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val boardSizeDp = configuration.screenWidthDp / 3
    val boardSize = (boardSizeDp * density).toInt()
    val boardBitmap = remember { Utils.loadBitmap(context, R.drawable.chess_board) }
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
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawBitmap(
                    boardBitmap,
                    null,
                    android.graphics.RectF(0f, 0f, size.width, size.width),
                    null
                )
            }

            var rowIndex = 0
            rows.forEach { rowString ->
                var colIndex = 0
                rowString.forEach { char ->
                    if (char.isDigit()) {
                        colIndex += (char - '0')
                    } else {
                        val bitmap = list[getPieceDrawableId(char)]
                        val left = colIndex * cellSize
                        val top = rowIndex * cellSize
                        drawIntoCanvas { canvas ->
                            canvas.nativeCanvas.drawBitmap(
                                bitmap,
                                null,
                                android.graphics.RectF(
                                    left,
                                    top,
                                    left + cellSize,
                                    top + cellSize
                                ),
                                null
                            )
                        }
                        colIndex++
                    }
                }
                rowIndex++
            }
        }
    }
}

fun pieceToInt(p: Char): Int {
    return when(p) {
        'P' -> 0
        'N' -> 1
        'B' -> 2
        'R' -> 3
        'Q' -> 4
        'K' -> 5
        'p' -> 6
        'n' -> 7
        'b' -> 8
        'r' -> 9
        'q' -> 10
        'k' -> 11
        else -> -1
    }
}

external fun makeMove(source: Int, sourcePiece: Char, target: Int, targetPiece: Char, toPiece: Char): MoveResult
external fun getLegalMoves(square: Int): IntArray
external fun parseFen(fen: String)
external fun hasOneLegalMove() : Boolean
