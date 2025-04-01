package com.huy.chess.utils

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.huy.chess.model.Piece

object Utils {

    fun initBoard(): List<MutableList<Piece>> {
        return List(8) { row ->
            mutableStateListOf(*Array(8) { col -> getInitialPiece(row, col) })
        }
    }
    private fun getInitialPiece(row: Int, col: Int): Piece {
        return Piece(row, col, when (row) {
            0 -> {
                when (col) {
                    0, 7 -> 'r'
                    1, 6 -> 'n'
                    2, 5 -> 'b'
                    3 -> 'q'
                    4 -> 'k'
                    else -> ' '
                }
            }
            1 -> 'p'
            6 -> 'P'
            7 -> when(col) {
                0, 7 -> 'R'
                1, 6 -> 'N'
                2, 5 -> 'B'
                3 -> 'Q'
                4 -> 'K'
                else -> ' '
            }
            else -> ' '
        })
    }

    fun loadImageBimap(context: Context, @DrawableRes resId: Int): ImageBitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        return bitmap.asImageBitmap()
    }
}