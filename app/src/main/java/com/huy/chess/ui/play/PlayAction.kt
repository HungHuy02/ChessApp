package com.huy.chess.ui.play

import com.huy.chess.utils.enums.GameResult

sealed class PlayAction {
    data object ClickedMore: PlayAction()
    data object ClickedAdd: PlayAction()
    data object ClickedBack: PlayAction()
    data object ClickedForward: PlayAction()
    data object ClickedBackButton: PlayAction()
    data class PieceCaptured(val piece: Char): PlayAction()
    data class Move(val move: String) : PlayAction()
    data class Result(val gameResult: GameResult): PlayAction()
}