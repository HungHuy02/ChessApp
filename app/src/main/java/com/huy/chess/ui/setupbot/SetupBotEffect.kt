package com.huy.chess.ui.setupbot

sealed class SetupBotEffect {
    data object PopBackStack: SetupBotEffect()
}