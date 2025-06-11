package com.huy.chess.data.model.response

import com.huy.chess.data.model.History
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetHistoriesResponse (
    val historyItems: List<History>
)