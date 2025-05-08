package com.huy.chess.ui.solvepuzzles

sealed class SolvePuzzlesAction {
    data object ClickedBack: SolvePuzzlesAction()
    data object ClickedSuggestion: SolvePuzzlesAction()
    data object ClickedRetry: SolvePuzzlesAction()
    data object ClickedContinue: SolvePuzzlesAction()
    data object ClickedAnswer: SolvePuzzlesAction()
}