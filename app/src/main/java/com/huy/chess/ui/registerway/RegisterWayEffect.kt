package com.huy.chess.ui.registerway

sealed class RegisterWayEffect {
    data object NavigateToEmailInput : RegisterWayEffect()
    data object SignInGoogle: RegisterWayEffect()
    data object SignInFacebook: RegisterWayEffect()
    data object NavigateLogin: RegisterWayEffect()
    data object PopBackStack: RegisterWayEffect()
}