package com.huy.chess.ui.changetime

import com.huy.chess.utils.enums.TimeType

data class ChangeTimeState (
    val isMoreSetting: Boolean = false,
    val selectedTime: TimeType = TimeType.TEN_MINUTES
)