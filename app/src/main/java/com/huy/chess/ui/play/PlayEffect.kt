package com.huy.chess.ui.play

import com.huy.chess.utils.enums.GameResult

sealed class PlayEffect {
    data object PopBackStack: PlayEffect()
    data class ShowEndGameDialog(val gameResult: GameResult): PlayEffect()
}