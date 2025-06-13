package com.huy.chess.ui.home

sealed class HomeAction {
    data object ClickedDailyPuzzle: HomeAction()
    data object ClickedPlayOnline: HomeAction()
    data object ClickedPuzzle: HomeAction()
    data object ClickedBot: HomeAction()
    data object ClickedStudy: HomeAction()
    data object ClickedPlay: HomeAction()
    data class ClickedHistory(val id: Long): HomeAction()
}