package com.huy.chess.ui.dialog.playonline

sealed class PlayOnlineEffect {
    data object PopBackStack: PlayOnlineEffect()
    data object NavigateWaiting: PlayOnlineEffect()
}