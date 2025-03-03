package com.huy.chess.contract

data class LoginState(
    val account: String = "",
    val password: String = "",
    val isButtonEnable: Boolean = false
)

sealed class LoginIntent {
    data class AccountChange(val text: String) : LoginIntent()
    data class PasswordChange(val text: String) : LoginIntent()
    data object ClickedLoginButton : LoginIntent()
    data object ClickedLoginGoogleButton : LoginIntent()
    data object ClickedLoginFacebookButton : LoginIntent()
}

sealed class LoginEvent {
    data object NavigateToHome : LoginEvent ()
}