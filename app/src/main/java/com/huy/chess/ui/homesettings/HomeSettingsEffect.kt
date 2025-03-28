package com.huy.chess.ui.homesettings

sealed class HomeSettingsEffect {
    data object PopBackStack : HomeSettingsEffect()
}