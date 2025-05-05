package com.huy.chess.ui.login

data class LoginState(
    val account: String = "",
    val password: String = "",
    val isButtonEnable: Boolean = false,
    val enableAccountError: Boolean = false,
    val enablePasswordError: Boolean = false,
    val showError: Boolean = false,
    val showSocialLogin: Boolean = true
)