package com.huy.chess.ui.dialog.endgame

sealed class EndGameAction {
    data object ClickedClose: EndGameAction()
    data object ClickedShare: EndGameAction()
    data object ClickedWatchPlay: EndGameAction()
    data object ClickedReplay: EndGameAction()
    data object ClickedNew: EndGameAction()
}