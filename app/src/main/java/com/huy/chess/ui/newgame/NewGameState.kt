package com.huy.chess.ui.newgame

import com.huy.chess.utils.enums.TimeType

data class NewGameState(
    val isShowMore: Boolean = false,
    val selectedTime: TimeType = TimeType.TEN_MINUTES
)