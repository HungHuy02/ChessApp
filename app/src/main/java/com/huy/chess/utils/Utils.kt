package com.huy.chess.utils

import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.huy.chess.model.Piece

object Utils {

    fun initBoard() : Array<Array<Piece>> {
        return arrayOf(
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
    }

    fun loadImageBimap(context: Context, @DrawableRes resId: Int): ImageBitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        return bitmap.asImageBitmap()
    }
}