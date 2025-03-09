package com.huy.chess.ui.login

sealed class LoginEffect {
    data object NavigateToHome : LoginEffect()
    data object SignInGoogle: LoginEffect()
    data object SignInFacebook: LoginEffect()
}