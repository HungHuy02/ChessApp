package com.huy.chess.ui.setupbot

import com.huy.chess.utils.enums.Side
import com.huy.chess.utils.enums.StockfishBotLevel
import com.huy.chess.utils.enums.TimeType

sealed class SetupBotAction {
    data object ClickShowMore: SetupBotAction()
    data class ClickedButton(val timeType: TimeType): SetupBotAction()
    data object ClickedBack: SetupBotAction()
    data class ClickedSide(val side: Side): SetupBotAction()
    data class ClickedLevel(val level: StockfishBotLevel): SetupBotAction()
    data object ClickedPlay: SetupBotAction()
}