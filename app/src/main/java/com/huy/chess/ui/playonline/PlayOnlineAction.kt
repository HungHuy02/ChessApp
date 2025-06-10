package com.huy.chess.ui.playonline

import com.huy.chess.data.model.Move

sealed class PlayOnlineAction {
    data object ClickedMore: PlayOnlineAction()
    data object ClickedAdd: PlayOnlineAction()
    data object ClickedBack: PlayOnlineAction()
    data object ClickedForward: PlayOnlineAction()
    data object ClickedBackButton: PlayOnlineAction()
    data class PieceCaptured(val piece: Char): PlayOnlineAction()
    data class OnMove(val move: String, val moveMaterial: Move?) : PlayOnlineAction()
    data class Result(val result: Int, val whiteSide: Boolean): PlayOnlineAction()
}