package com.huy.chess.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Puzzle(
    @Json(name = "fen") val fen: String,
    @Json(name = "moves") val moves: String,
)