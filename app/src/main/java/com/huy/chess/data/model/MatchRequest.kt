package com.huy.chess.data.model

data class MatchRequest(
    val id: String,
    val name: String,
    val avatar: String,
    val elo: Int
)
