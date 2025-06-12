package com.huy.chess.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Puzzle(
    @Json(name = "_id") val id: String,
    @Json(name = "fen") val fen: String,
    @Json(name = "moves") val moves: String,
    @Json(name = "rating") val rating: Int,
    @Json(name = "themes") val themes: String
)