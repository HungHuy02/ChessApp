package com.huy.chess.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchRequest(
    val id: String,
    val elo: Int,
    val time: Int
)
