package com.huy.chess.ui.emailinput

sealed class EmailInputAction {
    data class ClickedButton(val email: String) : EmailInputAction()
    data object InputChange : EmailInputAction()
    data object ClickedLogin: EmailInputAction()
}
