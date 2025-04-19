package com.huy.chess.ui.dailypuzzle

sealed class DailyPuzzleAction {
    data object ClickedBackButton: DailyPuzzleAction()
    data object ClickedHint: DailyPuzzleAction()
    data object ClickedReset: DailyPuzzleAction()
    data object ClickedAnalysis: DailyPuzzleAction()
    data object ClickedBack: DailyPuzzleAction()
    data object ClickedForward: DailyPuzzleAction()
    data object ClickedDate: DailyPuzzleAction()
}