package com.huy.chess.ui.playbot

sealed class PlayBotAction {
    data object ClickedMore: PlayBotAction()
    data object ClickedAdd: PlayBotAction()
    data object ClickedBack: PlayBotAction()
    data object ClickedForward: PlayBotAction()
    data object ClickedBackButton: PlayBotAction()
    data class PieceCaptured(val piece: Char): PlayBotAction()
    data class Move(val move: String, val fen: String) : PlayBotAction()
    data class Result(val result: Int, val whiteSide: Boolean): PlayBotAction()
}