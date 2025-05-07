package com.huy.chess.ui.dialog.playonline

sealed class PlayOnlineAction {
    data object ClickedCancel: PlayOnlineAction()
    data object ClickedStart: PlayOnlineAction()
}