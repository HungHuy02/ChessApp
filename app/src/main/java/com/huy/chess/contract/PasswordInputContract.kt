package com.huy.chess.contract

data class PasswordInputState(
    val inputText: String = "",
    val isButtonEnable: Boolean = false
)

sealed class PasswordInputIntent {
    data class InputChanged(val text: String) : PasswordInputIntent()
    data object ClickedButton : PasswordInputIntent()
}

sealed class PasswordInputEvent {
    data object NavigateToProfileSetup : PasswordInputEvent ()
}