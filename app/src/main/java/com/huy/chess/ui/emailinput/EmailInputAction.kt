package com.huy.chess.ui.emailinput

sealed class EmailInputAction {
    data class InputChanged(val text: String) : EmailInputAction()
    data object ClickedButton : EmailInputAction()
}
