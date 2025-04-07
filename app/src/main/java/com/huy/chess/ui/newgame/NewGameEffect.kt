package com.huy.chess.ui.newgame

sealed class NewGameEffect {
    data object NavigateChangeTime: NewGameEffect()
    data object NavigatePlay: NewGameEffect()
    data object NavigateSetupTwo: NewGameEffect()
    data object NavigateSetupBot: NewGameEffect()
    data object NavigateCustom: NewGameEffect()
    data object PopBackStack: NewGameEffect()
}