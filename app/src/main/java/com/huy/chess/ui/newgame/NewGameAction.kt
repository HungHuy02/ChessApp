package com.huy.chess.ui.newgame

sealed class NewGameAction {
    data object ClickedChangeTime: NewGameAction()
    data object ClickedPlay: NewGameAction()
    data object ClickedBot: NewGameAction()
    data object ClickedMore: NewGameAction()
    data object ClickedCustom: NewGameAction()
    data object ClickedTwo: NewGameAction()
    data object ClickedBack: NewGameAction()
}