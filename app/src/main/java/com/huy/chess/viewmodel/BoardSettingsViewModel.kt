package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.boardsettings.BoardSettingsAction
import com.huy.chess.ui.boardsettings.BoardSettingsEffect
import com.huy.chess.ui.boardsettings.BoardSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardSettingsViewModel @Inject constructor() : BaseViewModel<BoardSettingsState, BoardSettingsAction, BoardSettingsEffect>(BoardSettingsState.Default) {

    override fun processAction(action: BoardSettingsAction) {
        when(action) {
            BoardSettingsAction.ClickedBack -> sendEffect(BoardSettingsEffect.PopBackStack)
            BoardSettingsAction.ToggleGameResultBoardAnimations -> TODO()
            BoardSettingsAction.ToggleHapticFeedback -> TODO()
            BoardSettingsAction.ToggleHighlightLastMove -> TODO()
            BoardSettingsAction.ToggleMagnifyPieces -> TODO()
            BoardSettingsAction.ToggleShowCoordinates -> TODO()
            BoardSettingsAction.ToggleShowLegalMove -> TODO()
            BoardSettingsAction.ToggleSounds -> TODO()
        }
    }
}