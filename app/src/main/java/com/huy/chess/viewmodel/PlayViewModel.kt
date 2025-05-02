package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.ui.play.PlayEffect
import com.huy.chess.ui.play.PlayState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.increment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor() :
    BaseViewModel<PlayState, PlayAction, PlayEffect>(PlayState()) {

    init {
        parseFen(Constants.START_FEN)
    }

    override fun processAction(action: PlayAction) {
        when(action) {
            PlayAction.ClickedAdd -> {}
            PlayAction.ClickedBack -> {}
            PlayAction.ClickedForward -> {}
            PlayAction.ClickedMore -> sendEffect(PlayEffect.ShowPlayOptionsDialog)
            PlayAction.ClickedBackButton -> sendEffect(PlayEffect.PopBackStack)
            is PlayAction.PieceCaptured -> {
                updateState {
                    val map = state.value.capturedPiece
                    map.increment(action.piece)
                    it.copy(capturedPiece = map)
                }
            }
        }
    }
}