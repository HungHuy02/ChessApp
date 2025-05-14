package com.huy.chess.ui.playonline

import com.huy.chess.utils.enums.GameResult

sealed class PlayOnlineEffect {
    data object PopBackStack: PlayOnlineEffect()
    data object ShowPlayOptionsDialog: PlayOnlineEffect()
    data class ShowEndGameDialog(val gameResult: GameResult): PlayOnlineEffect()
}