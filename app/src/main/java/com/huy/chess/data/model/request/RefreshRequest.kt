package com.huy.chess.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshRequest(
    @Json(name = "uuid") val uuid : String
)