package com.huy.chess.ui.boardsettings

sealed class BoardSettingsEffect {
    data object PopBackStack : BoardSettingsEffect()
}