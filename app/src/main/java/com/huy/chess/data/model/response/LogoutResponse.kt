package com.huy.chess.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutResponse (
    val loggedOut: Boolean
)