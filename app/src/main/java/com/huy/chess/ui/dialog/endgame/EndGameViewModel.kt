package com.huy.chess.ui.dialog.endgame

import com.huy.chess.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EndGameViewModel @Inject constructor() : BaseViewModel<EndGameState, EndGameAction, EndGameEffect>(EndGameState.Default) {
    override fun processAction(action: EndGameAction) {
        when(action) {
            EndGameAction.ClickedClose -> sendEffect(EndGameEffect.PopBackStack)
            EndGameAction.ClickedNew -> {}
            EndGameAction.ClickedReplay -> {}
            EndGameAction.ClickedShare -> {}
            EndGameAction.ClickedWatchPlay -> {}
        }
    }

}