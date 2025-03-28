package com.huy.chess.ui.boardsettings

sealed class BoardSettingsState {
    data object Default: BoardSettingsState()
}