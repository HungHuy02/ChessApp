package com.huy.chess.ui.changetime

sealed class ChangeTimeAction {
    data object ToggleShowMore : ChangeTimeAction()
    data class ClickedButton(val selectedTime: String) : ChangeTimeAction()
}
