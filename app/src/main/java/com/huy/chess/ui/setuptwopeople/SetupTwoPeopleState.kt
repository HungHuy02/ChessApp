package com.huy.chess.ui.setuptwopeople

import com.huy.chess.utils.enums.TimeType

data class SetupTwoPeopleState (
    val showTimeControl: Boolean = false,
    val selectedTime: TimeType = TimeType.UNLIMITED
)