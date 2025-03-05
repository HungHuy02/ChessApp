package com.huy.chess.ui.emailinput

sealed class EmailInputEffect {
    data object NavigateToPasswordInput : EmailInputEffect()
}