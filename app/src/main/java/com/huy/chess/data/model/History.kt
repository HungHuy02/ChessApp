package com.huy.chess.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class History (
    @Json(name = "_id") val id: String,
    val ids: List<String>,
    val white: String,
    val black: String,
    val whiteElo: Int,
    val blackElo: Int,
    val timeControl: String,
    val notation: String,
    val blackRatingDiff: String,
    val result: String,
    val whiteRatingDiff: String,
    val createdAt: Long,
    val updatedAt: Long?
)