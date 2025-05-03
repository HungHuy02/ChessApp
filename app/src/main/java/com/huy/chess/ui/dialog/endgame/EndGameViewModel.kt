package com.huy.chess.ui.dialog.endgame

import com.huy.chess.base.BaseViewModel
import com.huy.chess.utils.toResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EndGameViewModel @Inject constructor() : BaseViewModel<EndGameState, EndGameAction, EndGameEffect>(EndGameState()) {
    override fun processAction(action: EndGameAction) {
        when(action) {
            EndGameAction.ClickedClose -> sendEffect(EndGameEffect.PopBackStack)
            EndGameAction.ClickedNew -> {}
            EndGameAction.ClickedReplay -> {}
            EndGameAction.ClickedShare -> {}
            EndGameAction.ClickedWatchPlay -> {}
            is EndGameAction.UpdateResult -> updateState { it.copy(gameResultInfo = action.gameResult.toResult()) }
        }
    }

}