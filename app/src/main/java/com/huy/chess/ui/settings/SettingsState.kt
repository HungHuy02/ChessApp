package com.huy.chess.ui.settings

sealed class SettingsState {
    data object Default: SettingsState()
}