package com.huy.chess.ui.gamearchive

sealed class GameArchiveAction {
    data object ClickedBack: GameArchiveAction()
    data object ClickedSearch: GameArchiveAction()
}