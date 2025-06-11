package com.huy.chess.data.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutRequest(
    val uuid: String
)
