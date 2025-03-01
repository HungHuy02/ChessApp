package com.huy.chess.model.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String
)
