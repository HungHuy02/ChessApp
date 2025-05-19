package com.huy.chess.viewmodel

import android.util.Log
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
            PlayAction.ClickedBack -> {
                if(state.value.currentFen > 0)
                    updateState {
                        val index = it.currentFen - 1
                        Log.e("tag", "$index")
                        it.copy(
                            currentFen = index,
                            displayFen = it.listFen[index]
                        )
                    }
            }
            PlayAction.ClickedForward -> {
                if(state.value.currentFen < state.value.listFen.size - 1)
                    updateState {
                        val index = it.currentFen + 1
                        Log.e("tag", "$index")
                        it.copy(
                            currentFen = index,
                            displayFen = it.listFen[index]
                        )
                    }
            }
            PlayAction.ClickedMore -> updateState { it.copy(showDialog = true) }
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
                val listFen = it.listFen
                listFen.add(action.fen)
                Log.e("tag", action.fen)
                if(it.autoRotate)
                    it.copy(
                        notationList = notation,
                        topName = it.bottomName,
                        bottomName = it.topName,
                        topAvatar = it.bottomAvatar,
                        bottomAvatar = it.topAvatar,
                        bottomSide = !it.bottomSide,
                        listFen = listFen,
                        currentFen = it.currentFen + 1,
                        displayFen = null
                    )
                else
                    it.copy(
                        notationList = notation,
                        listFen = listFen,
                        currentFen = it.currentFen + 1,
                        displayFen = null
                    )
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
            PlayAction.ClickedCopyPgn -> { updateState { it.copy(showDialog = false) } }
            PlayAction.ClickedRotate -> updateState { it.copy(autoRotate = !it.autoRotate, showDialog = false) }
            PlayAction.ClickedSurrender -> { updateState { it.copy(showDialog = false) } }
            PlayAction.CloseDialog -> updateState { it.copy(showDialog = false) }
        }
    }
}