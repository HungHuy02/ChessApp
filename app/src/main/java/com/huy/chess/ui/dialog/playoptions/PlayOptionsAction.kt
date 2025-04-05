package com.huy.chess.ui.dialog.playoptions

sealed class PlayOptionsAction {
    data object ClickedClose: PlayOptionsAction()
    data object ClickedFlip: PlayOptionsAction()
    data object ClickedExplorer: PlayOptionsAction()
    data object ClickedShare: PlayOptionsAction()
    data object ClickedSave: PlayOptionsAction()
}