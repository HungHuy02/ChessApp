package com.huy.chess.ui.changetime

import com.huy.chess.utils.enums.TimeType

sealed class ChangeTimeAction {
    data object ToggleShowMore : ChangeTimeAction()
    data class ClickedButton(val selectedTime: TimeType) : ChangeTimeAction()
    data object ClickedBack : ChangeTimeAction()
}
