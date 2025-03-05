package com.huy.chess.ui.login

sealed class LoginEffect {
    data object NavigateToHome : LoginEffect()
}