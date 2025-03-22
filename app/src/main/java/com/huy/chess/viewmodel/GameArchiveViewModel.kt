package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.gamearchive.GameArchiveAction
import com.huy.chess.ui.gamearchive.GameArchiveEffect
import com.huy.chess.ui.gamearchive.GameArchiveState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameArchiveViewModel @Inject constructor() :
    BaseViewModel<GameArchiveState, GameArchiveAction, GameArchiveEffect>(GameArchiveState.Default) {
    override fun processAction(action: GameArchiveAction) {
        when(action) {
            GameArchiveAction.ClickedBack -> sendEffect(GameArchiveEffect.PopBackStack)
            GameArchiveAction.ClickedSearch -> {}
        }
    }

}