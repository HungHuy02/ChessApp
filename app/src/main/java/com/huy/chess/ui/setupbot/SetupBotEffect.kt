package com.huy.chess.ui.setupbot

import com.huy.chess.utils.enums.StockfishBotLevel

sealed class SetupBotEffect {
    data object PopBackStack: SetupBotEffect()
    data class NavigatePlayBot(val level: StockfishBotLevel): SetupBotEffect()
}