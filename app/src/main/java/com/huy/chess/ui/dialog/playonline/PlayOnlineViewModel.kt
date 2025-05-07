package com.huy.chess.ui.dialog.playonline

import com.huy.chess.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayOnlineViewModel @Inject constructor() : BaseViewModel<PlayOnlineState, PlayOnlineAction, PlayOnlineEffect>(PlayOnlineState.Default) {
    override fun processAction(action: PlayOnlineAction) {
        when(action) {
            PlayOnlineAction.ClickedCancel -> sendEffect(PlayOnlineEffect.PopBackStack)
            PlayOnlineAction.ClickedStart -> sendEffect(PlayOnlineEffect.NavigateWaiting)
        }
    }
}