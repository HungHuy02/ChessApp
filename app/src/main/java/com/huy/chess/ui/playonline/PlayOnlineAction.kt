package com.huy.chess.ui.playonline

sealed class PlayOnlineAction {
    data object ClickedMore: PlayOnlineAction()
    data object ClickedAdd: PlayOnlineAction()
    data object ClickedBack: PlayOnlineAction()
    data object ClickedForward: PlayOnlineAction()
    data object ClickedBackButton: PlayOnlineAction()
    data class PieceCaptured(val piece: Char): PlayOnlineAction()
    data class Move(val move: String, val fen: String?) : PlayOnlineAction()
    data class Result(val result: Int, val whiteSide: Boolean): PlayOnlineAction()
}