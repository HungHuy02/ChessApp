package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.ui.play.PlayEffect
import com.huy.chess.ui.play.PlayState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor() :
    BaseViewModel<PlayState, PlayAction, PlayEffect>(PlayState()) {

    init {
        System.loadLibrary("chess")
        parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
    }

    override fun processAction(action: PlayAction) {
        when(action) {
            PlayAction.ClickedAdd -> {}
            PlayAction.ClickedBack -> {}
            PlayAction.ClickedForward -> {}
            PlayAction.ClickedMore -> sendEffect(PlayEffect.ShowPlayOptionsDialog)
            PlayAction.ClickedBackButton -> sendEffect(PlayEffect.PopBackStack)
        }
    }
}