package com.huy.chess.ui.history

sealed class HistoryAction {
    data object ClickedMore: HistoryAction()
    data object ClickedBack: HistoryAction()
    data object ClickedForward: HistoryAction()
    data object ClickedBackButton: HistoryAction()
    data object ClickedCopyPgn: HistoryAction()
    data object ClickedRotate: HistoryAction()
    data object CloseDialog: HistoryAction()
    data class ClickedNotation(val index: Int): HistoryAction()
    data class UpdateId(val id: Long): HistoryAction()
}