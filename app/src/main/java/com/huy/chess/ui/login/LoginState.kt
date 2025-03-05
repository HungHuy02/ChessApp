package com.huy.chess.ui.login

data class LoginState(
    val account: String = "",
    val password: String = "",
    val isButtonEnable: Boolean = false
)