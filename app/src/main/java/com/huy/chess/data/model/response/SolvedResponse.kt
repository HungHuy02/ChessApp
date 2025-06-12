package com.huy.chess.data.model.response

import com.huy.chess.data.model.Puzzle
import com.huy.chess.data.model.SolvedPuzzle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SolvedResponse(
    @Json(name = "newElo") val newElo: Int,
    @Json(name = "solvedPuzzle") val solvedPuzzle: SolvedPuzzle,
    @Json(name = "newPuzzle") val newPuzzle: Puzzle
)