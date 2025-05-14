package com.huy.chess.viewmodel

import android.util.Log
import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.playbot.PlayBotAction
import com.huy.chess.ui.playbot.PlayBotEffect
import com.huy.chess.ui.playbot.PlayBotState
import com.huy.chess.ui.playonline.PlayOnlineAction
import com.huy.chess.ui.playonline.PlayOnlineEffect
import com.huy.chess.ui.playonline.PlayOnlineState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.utils.increment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayOnlineViewModel @Inject constructor() :
    BaseViewModel<PlayOnlineState, PlayOnlineAction, PlayOnlineEffect>(PlayOnlineState()) {

    init {
        parseFen(Constants.START_FEN)
    }

    override fun processAction(action: PlayOnlineAction) {
        when(action) {
            PlayOnlineAction.ClickedAdd -> {}
            PlayOnlineAction.ClickedBack -> {}
            PlayOnlineAction.ClickedForward -> {}
            PlayOnlineAction.ClickedMore -> sendEffect(PlayOnlineEffect.ShowPlayOptionsDialog)
            PlayOnlineAction.ClickedBackButton -> sendEffect(PlayOnlineEffect.PopBackStack)
            is PlayOnlineAction.PieceCaptured -> {
                updateState {
                    val map = state.value.capturedPiece
                    map.increment(action.piece)
                    it.copy(capturedPiece = map)
                }
            }
            is PlayOnlineAction.Move -> {
                Log.e("tag", action.fen ?: "null")
                updateState {
                    val notation = state.value.notationList.toMutableList()
                    notation.add(action.move)
                    it.copy(notationList = notation, nextMove = null)
                }

            }
            is PlayOnlineAction.Result -> {
                val gameResult = when(action.result) {
                    2 -> if(action.whiteSide) GameResult.WIN_CHECKMATE else GameResult.LOSE_CHECKMATE
                    3 -> GameResult.DRAW_STALEMATE
                    4 -> GameResult.DRAW_THREEFOLD_REPETITION
                    5 -> GameResult.DRAW_FIFTY_MOVE_RULE
                    else -> GameResult.DRAW_INSUFFICIENT_MATERIAL
                }
                sendEffect(PlayOnlineEffect.ShowEndGameDialog(gameResult))
            }
        }
    }
}