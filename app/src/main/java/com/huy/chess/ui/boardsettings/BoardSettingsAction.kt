package com.huy.chess.ui.boardsettings

sealed class BoardSettingsAction {
    data object ClickedBack: BoardSettingsAction()
    data object ToggleSounds: BoardSettingsAction()
    data object ToggleHapticFeedback: BoardSettingsAction()
    data object ToggleShowCoordinates: BoardSettingsAction()
    data object ToggleHighlightLastMove: BoardSettingsAction()
    data object ToggleShowLegalMove: BoardSettingsAction()
    data object ToggleMagnifyPieces: BoardSettingsAction()
    data object ToggleGameResultBoardAnimations: BoardSettingsAction()
}