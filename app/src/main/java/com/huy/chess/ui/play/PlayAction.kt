package com.huy.chess.ui.play

sealed class PlayAction {
    data object ClickedMore: PlayAction()
    data object ClickedAnalysis: PlayAction()
    data object ClickedBack: PlayAction()
    data object ClickedForward: PlayAction()
    data object ClickedBackButton: PlayAction()
    data class PieceCaptured(val piece: Char): PlayAction()
    data class Move(val move: String, val fen: String) : PlayAction()
    data class Result(val result: Int, val whiteSide: Boolean): PlayAction()
    data object ClickedCopyPgn: PlayAction()
    data object ClickedRotate: PlayAction()
    data object ClickedSurrender: PlayAction()
    data object CloseDialog: PlayAction()
    data class ClickedNotation(val index: Int): PlayAction()
}