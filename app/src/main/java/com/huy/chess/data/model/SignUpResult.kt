package com.huy.chess.data.model

sealed interface SignUpResult {
    data class Success(val username: String): SignUpResult
    data object Cancelled: SignUpResult
    data object Failure: SignUpResult
}