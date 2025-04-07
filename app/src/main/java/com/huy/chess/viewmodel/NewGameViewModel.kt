package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.newgame.NewGameAction
import com.huy.chess.ui.newgame.NewGameEffect
import com.huy.chess.ui.newgame.NewGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor() :
    BaseViewModel<NewGameState, NewGameAction, NewGameEffect>(NewGameState()) {
    override fun processAction(action: NewGameAction) {
        when (action) {
            NewGameAction.ClickedBot -> sendEffect(NewGameEffect.NavigateSetupBot)
            NewGameAction.ClickedChangeTime -> sendEffect(NewGameEffect.NavigateChangeTime)
            NewGameAction.ClickedCustom -> sendEffect(NewGameEffect.NavigateCustom)
            NewGameAction.ClickedMore -> updateState { it.copy(isShowMore = !it.isShowMore) }
            NewGameAction.ClickedPlay -> sendEffect(NewGameEffect.NavigatePlay)
            NewGameAction.ClickedTwo -> sendEffect(NewGameEffect.NavigateSetupTwo)
            NewGameAction.ClickedBack -> sendEffect(NewGameEffect.PopBackStack)
        }
    }
}