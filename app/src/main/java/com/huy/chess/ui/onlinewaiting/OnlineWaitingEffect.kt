package com.huy.chess.ui.onlinewaiting

sealed class OnlineWaitingEffect {
    data object PopBackStack: OnlineWaitingEffect()
    data object NavigatePlayOnline: OnlineWaitingEffect()
}