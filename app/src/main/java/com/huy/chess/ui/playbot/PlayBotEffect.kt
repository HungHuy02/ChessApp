package com.huy.chess.ui.playbot

import com.huy.chess.utils.enums.GameResult

sealed class PlayBotEffect {
    data object PopBackStack: PlayBotEffect()
    data object ShowPlayOptionsDialog: PlayBotEffect()
    data class ShowEndGameDialog(val gameResult: GameResult): PlayBotEffect()
}