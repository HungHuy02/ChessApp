package com.huy.chess.ui.changetime

sealed class ChangeTimeEffect {
    data object PopBackStack: ChangeTimeEffect()
}