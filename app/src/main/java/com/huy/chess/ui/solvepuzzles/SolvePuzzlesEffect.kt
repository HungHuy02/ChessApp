package com.huy.chess.ui.solvepuzzles

sealed class SolvePuzzlesEffect {
    data object PopBackStack: SolvePuzzlesEffect()
}