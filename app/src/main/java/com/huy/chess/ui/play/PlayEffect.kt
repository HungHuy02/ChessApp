package com.huy.chess.ui.play

sealed class PlayEffect {
    data object PopBackStack: PlayEffect()
    data object ShowPlayOptionsDialog: PlayEffect()
    data object ShowEndGameDialog: PlayEffect()
}