package com.huy.chess.ui.passwordinput

sealed class PasswordInputEffect {
    data object NavigateToProfileSetup : PasswordInputEffect()
}