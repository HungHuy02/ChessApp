package com.huy.chess.ui.puzzlessettings

sealed class PuzzlesSettingsEffect {
    data object PopBackStack : PuzzlesSettingsEffect()
}