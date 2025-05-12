package com.huy.chess.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyPuzzle (
    @Json(name = "title") val title: String,
    @Json(name = "fen") val fen: String,
    @Json(name = "moves") val moves: String,
    @Json(name = "date") val date: String,
    @Json(name = "videoUrl") val videoUrl: String,
    @Json(name = "solvedCount") val solvedCount: Int,
    @Json(name = "commentCount") val commentCount: Int
)