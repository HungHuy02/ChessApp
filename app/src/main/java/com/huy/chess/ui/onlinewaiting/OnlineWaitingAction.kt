package com.huy.chess.ui.onlinewaiting

sealed class OnlineWaitingAction {
    data object ClickedBack: OnlineWaitingAction()
    data object ClickedCancel: OnlineWaitingAction()
}