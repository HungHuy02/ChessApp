package com.huy.chess.ui.dialog.endgame

sealed class EndGameEffect {
    data object PopBackStack: EndGameEffect()
}