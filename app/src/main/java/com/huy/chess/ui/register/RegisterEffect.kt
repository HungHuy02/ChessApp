package com.huy.chess.ui.register

sealed class RegisterEffect {
    data object NavigateToEmailInput : RegisterEffect()
    data object SignInGoogle: RegisterEffect()
    data object SignInFacebook: RegisterEffect()
}