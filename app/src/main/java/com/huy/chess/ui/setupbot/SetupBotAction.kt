package com.huy.chess.ui.setupbot

import com.huy.chess.utils.enums.TimeType

sealed class SetupBotAction {
    data object ClickShowMore: SetupBotAction()
    data class ClickedButton(val timeType: TimeType): SetupBotAction()
    data object ClickedBack: SetupBotAction()
}