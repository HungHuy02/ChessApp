package com.huy.chess.ui.login

sealed class LoginAction {
    data class AccountChange(val text: String) : LoginAction()
    data class PasswordChange(val text: String) : LoginAction()
    data object ClickedLoginButton : LoginAction()
    data object ClickedLoginGoogleButton : LoginAction()
    data object ClickedLoginFacebookButton : LoginAction()
    data object ClickedBackButton : LoginAction()
}