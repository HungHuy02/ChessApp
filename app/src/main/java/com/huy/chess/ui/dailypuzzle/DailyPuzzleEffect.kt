package com.huy.chess.ui.dailypuzzle

sealed class DailyPuzzleEffect {
    data object PopBackStack: DailyPuzzleEffect()
    data object NavigateSelectDate: DailyPuzzleEffect()
    data object ShowSuccessDialog: DailyPuzzleEffect()
}