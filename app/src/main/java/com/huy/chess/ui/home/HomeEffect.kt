package com.huy.chess.ui.home

sealed class HomeEffect {
    data object ShowPlayOnlineDialog: HomeEffect()
    data object NavigatePuzzle: HomeEffect()
    data object NavigateDailyPuzzle: HomeEffect()
    data object NavigatePlayBot: HomeEffect()
    data object NavigateStudy: HomeEffect()
    data class NavigateHistory(val id: Long): HomeEffect()
    data object NavigateGameArchive: HomeEffect()
    data object NavigatePlay: HomeEffect()
}