package com.huy.chess.ui.puzzle

sealed class PuzzleAction {
    data object ClickedSolvePuzzles: PuzzleAction()
    data object ClickedPuzzleRush: PuzzleAction()
    data object ClickedDailyPuzzle: PuzzleAction()
}