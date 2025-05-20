package com.huy.chess.ui.onlinewaiting

import com.huy.chess.utils.enums.TimeType

data class OnlineWaitingState (
    val timeType: TimeType = TimeType.TEN_MINUTES
)