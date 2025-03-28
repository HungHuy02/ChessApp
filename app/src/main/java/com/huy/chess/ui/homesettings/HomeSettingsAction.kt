package com.huy.chess.ui.homesettings

sealed class HomeSettingsAction {
    data object ClickedBack: HomeSettingsAction()
    data object ToggleShowFeaturedEv: HomeSettingsAction()
    data object ToggleShowRecommendGame: HomeSettingsAction()
    data object ToggleShowPlayOnline: HomeSettingsAction()
    data object ToggleShowPlayBots: HomeSettingsAction()
    data object ToggleShowPlayCoach: HomeSettingsAction()
    data object ToggleShowPlayLessons: HomeSettingsAction()
    data object ToggleShowPuzzles: HomeSettingsAction()
    data object ToggleShowDailyPuzzles: HomeSettingsAction()
}