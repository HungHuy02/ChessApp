package com.huy.chess.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Move(
    val source: Int,
    val sourcePiece: Int,
    val target: Int,
    val targetPiece: Int,
    val promote: Int
)
