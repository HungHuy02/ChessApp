package com.huy.chess.contract

data class EmailInputState(
    val inputText: String = "",
    val isButtonEnable: Boolean = false
)

sealed class EmailInputIntent {
    data class InputChanged(val text: String) : EmailInputIntent()
    data object ClickedButton : EmailInputIntent()
}

sealed class EmailInputEvent {
    data object NavigateToPasswordInput : EmailInputEvent()
}