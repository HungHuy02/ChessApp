package com.huy.chess.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SolvedRequest(
    @Json(name = "puzzleId") val puzzleId: String,
    @Json(name = "puzzleElo") val puzzleElo: Int,
    @Json(name = "elo") val elo: Int,
    @Json(name = "successMoves") val successMoves: Int,
    @Json(name = "totalMoves") val totalMoves: Int,
    @Json(name = "themes") val themes: String? = null
)
