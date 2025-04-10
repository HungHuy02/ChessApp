package com.huy.chess.ui.selectdate

sealed class SelectDateAction {
    data object ClickedDate: SelectDateAction()
}