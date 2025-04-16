package com.huy.chess.data.model.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String?
)
