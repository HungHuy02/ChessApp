package com.huy.chess.contract

data class ChangeTimeState (
    val isMoreSetting: Boolean = false,
    val selectedTime: String = ""
)

sealed class ChangeTimeIntent {
    data object ToggleShowMore : ChangeTimeIntent()
    data class ClickedButton(val selectedTime: String) : ChangeTimeIntent()
}

sealed class ChangeTimeEvent