package com.huy.chess.ui.puzzlessettings

sealed class PuzzlesSettingsAction {
    data object ClickedBack: PuzzlesSettingsAction()
    data object ToggleCollectPoints: PuzzlesSettingsAction()
    data object ToggleShowRating: PuzzlesSettingsAction()
    data object ToggleShowTimer: PuzzlesSettingsAction()
}