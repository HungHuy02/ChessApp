package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.puzzlessettings.PuzzlesSettingsAction
import com.huy.chess.ui.puzzlessettings.PuzzlesSettingsEffect
import com.huy.chess.ui.puzzlessettings.PuzzlesSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PuzzlesSettingsViewModel @Inject constructor() : BaseViewModel<PuzzlesSettingsState, PuzzlesSettingsAction, PuzzlesSettingsEffect>(PuzzlesSettingsState.Default) {
    override fun processAction(action: PuzzlesSettingsAction) {
        when(action) {
            PuzzlesSettingsAction.ClickedBack -> sendEffect(PuzzlesSettingsEffect.PopBackStack)
            PuzzlesSettingsAction.ToggleCollectPoints -> TODO()
            PuzzlesSettingsAction.ToggleShowRating -> TODO()
            PuzzlesSettingsAction.ToggleShowTimer -> TODO()
        }
    }

}