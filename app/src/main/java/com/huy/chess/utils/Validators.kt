package com.huy.chess.utils

const val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"

fun String.isValidEmail() : Boolean {
    return this.matches(Regex(EMAIL_REGEX))
}