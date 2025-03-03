package com.huy.chess.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse(
    @Json(name = "status") val status: Int,
    @Json(name = "message") val message: String
)
