package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.ui.play.PlayEffect
import com.huy.chess.ui.play.PlayState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.enums.GameResult
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
            is PlayAction.Move -> updateState {
                val notation = it.notationList.toMutableList()
                notation.add(action.move)
                if(it.autoRotate)
                    it.copy(
                        notationList = notation,
                        topName = it.bottomName,
                        bottomName = it.topName,
                        topAvatar = it.bottomAvatar,
                        bottomAvatar = it.topAvatar,
                        bottomSide = !it.bottomSide
                    )
                else
                    it.copy(notationList = notation)
            }
            is PlayAction.Result -> {
                updateState { it.copy(isEnd = true) }
                val gameResult = when(action.result) {
                    2 -> if(action.whiteSide) GameResult.WIN_CHECKMATE else GameResult.LOSE_CHECKMATE
                    3 -> GameResult.DRAW_STALEMATE
                    4 -> GameResult.DRAW_THREEFOLD_REPETITION
                    5 -> GameResult.DRAW_FIFTY_MOVE_RULE
                    else -> GameResult.DRAW_INSUFFICIENT_MATERIAL
                }
                sendEffect(PlayEffect.ShowEndGameDialog(gameResult))
            }
            PlayAction.ClickedAnalysis -> {}
        }
    }
}