package com.huy.chess.ui.dialog.playoptions

import com.huy.chess.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayOptionsViewModel @Inject constructor() :
BaseViewModel<PlayOptionsState, PlayOptionsAction, PlayOptionsEffect>(PlayOptionsState.Default) {
    override fun processAction(action: PlayOptionsAction) {
        when(action) {
            PlayOptionsAction.ClickedExplorer -> {}
            PlayOptionsAction.ClickedFlip -> {}
            PlayOptionsAction.ClickedSave -> {}
            PlayOptionsAction.ClickedShare -> {}
            PlayOptionsAction.ClickedClose -> sendEffect(PlayOptionsEffect.CloseDialog)
        }
    }

}