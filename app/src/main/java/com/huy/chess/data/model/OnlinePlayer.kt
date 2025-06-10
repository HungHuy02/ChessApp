package com.huy.chess.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OnlinePlayer (
    @Json(name = "_id") val id: String,
    val name: String,
    val elo: Int,
    val avatar: String?
)