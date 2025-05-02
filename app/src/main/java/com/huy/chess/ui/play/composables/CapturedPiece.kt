package com.huy.chess.ui.play.composables

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.huy.chess.ui.component.getChessPiecePainters
import com.huy.chess.utils.increment

@Composable
fun CapturedPiece(
    modifier: Modifier = Modifier,
    map: MutableMap<Char, Int>
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val boardSize = (configuration.screenWidthDp * density).toInt()
    val cellSize = boardSize / 14
    val pieceSize = cellSize / 3f

    val list = remember { getChessPiecePainters(context, cellSize) }
    Canvas(modifier = modifier) {
        var currentX = 0f
        listOf('p', 'n', 'b', 'r', 'q').forEach { pieceType ->
            map[pieceType]?.let { count ->
                currentX = drawPiece(
                    count = count,
                    imageBitmap = list[getPieceDrawableId(pieceType)],
                    x = currentX,
                    y = 0f,
                    distance = pieceSize
                )
            }
        }
        currentX = 0f
        listOf('P', 'N', 'B', 'R', 'Q').forEach { pieceType ->
            map[pieceType]?.let { count ->
                currentX = drawPiece(
                    count = count,
                    imageBitmap = list[getPieceDrawableId(pieceType)],
                    x = currentX,
                    y = cellSize * 1.2f,
                    distance = pieceSize
                )
            }
        }
    }
}

fun DrawScope.drawPiece(
    count: Int,
    imageBitmap: ImageBitmap,
    x: Float,
    y: Float,
    distance: Float
) : Float {
    var positionX = x + distance * 2
    for (i in 0 ..< count) {
        drawImage(
            image = imageBitmap,
            topLeft = Offset(positionX, y)
        )
        positionX += distance
    }
    return positionX
}

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