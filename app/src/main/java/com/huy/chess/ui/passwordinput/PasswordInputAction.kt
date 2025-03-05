package com.huy.chess.ui.passwordinput

sealed class PasswordInputAction {
    data class InputChanged(val text: String) : PasswordInputAction()
    data object ClickedButton : PasswordInputAction()
}
