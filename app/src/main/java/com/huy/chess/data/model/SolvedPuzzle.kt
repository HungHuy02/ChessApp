package com.huy.chess.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SolvedPuzzle(
    @Json(name = "userId") val userId: String,
    @Json(name = "puzzleId") val puzzleId: String,
    @Json(name = "ratingDiff") val ratingDiff: String,
    @Json(name = "solvedSuccess") val solvedSuccess: Boolean,
    @Json(name = "moveProgress") val moveProgress: String,
    @Json(name = "createdAt") val createdAt: Long,
    @Json(name = "updatedAt") val updatedAt: Long?,
)