package com.huy.chess.ui.gamearchive

sealed class GameArchiveEffect {
    data object PopBackStack : GameArchiveEffect()
}