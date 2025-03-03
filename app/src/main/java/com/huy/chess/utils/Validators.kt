package com.huy.chess.utils

const val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
private const val PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,}\$"

fun String.isValidEmail() : Boolean {
    return this.matches(Regex(EMAIL_REGEX))
}

fun String.isValidPassword() : Boolean {
    return this.matches(Regex(PASSWORD_REGEX))
}