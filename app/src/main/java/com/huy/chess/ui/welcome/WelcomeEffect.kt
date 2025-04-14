package com.huy.chess.ui.welcome

sealed class WelcomeEffect {
    data object NavigateHome: WelcomeEffect()
}