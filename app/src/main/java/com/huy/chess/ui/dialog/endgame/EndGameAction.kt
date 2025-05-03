package com.huy.chess.ui.dialog.endgame

import com.huy.chess.utils.enums.GameResult

sealed class EndGameAction {
    data object ClickedClose: EndGameAction()
    data object ClickedShare: EndGameAction()
    data object ClickedWatchPlay: EndGameAction()
    data object ClickedReplay: EndGameAction()
    data object ClickedNew: EndGameAction()
    data class UpdateResult(val gameResult: GameResult): EndGameAction()
}