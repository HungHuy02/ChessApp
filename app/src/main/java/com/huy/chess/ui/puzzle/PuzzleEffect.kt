package com.huy.chess.ui.puzzle

sealed class PuzzleEffect {
    data object NavigateSolvePuzzles: PuzzleEffect()
    data object NavigateSetupPuzzleRush: PuzzleEffect()
    data object NavigateDailyPuzzles: PuzzleEffect()
}