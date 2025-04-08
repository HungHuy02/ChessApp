package com.huy.chess.ui.setupbot

import com.huy.chess.utils.enums.TimeType

data class SetupBotState (
    val showTimeControl: Boolean = false,
    val selectedTime: TimeType = TimeType.UNLIMITED
)