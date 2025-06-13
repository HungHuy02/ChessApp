package com.huy.chess.ui.history

import com.huy.chess.utils.enums.GameResult

sealed class HistoryEffect {
    data object PopBackStack: HistoryEffect()
    data class ShowEndGameDialog(val gameResult: GameResult): HistoryEffect()
    data class CopyToClipBoard(val pgn: String): HistoryEffect()
}