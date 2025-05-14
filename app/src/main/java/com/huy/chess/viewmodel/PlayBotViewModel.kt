package com.huy.chess.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.service.StockfishService
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.playbot.PlayBotAction
import com.huy.chess.ui.playbot.PlayBotEffect
import com.huy.chess.ui.playbot.PlayBotState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.enums.GameResult
import com.huy.chess.utils.increment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayBotViewModel @Inject constructor(
    private val stockfishService: StockfishService
) :
    BaseViewModel<PlayBotState, PlayBotAction, PlayBotEffect>(PlayBotState()) {

    init {
        parseFen(Constants.START_FEN)
        viewModelScope.launch {
            stockfishService.setupNewGame(level = 2)
        }
    }

    override fun processAction(action: PlayBotAction) {
        when(action) {
            PlayBotAction.ClickedAdd -> {}
            PlayBotAction.ClickedBack -> {}
            PlayBotAction.ClickedForward -> {}
            PlayBotAction.ClickedMore -> sendEffect(PlayBotEffect.ShowPlayOptionsDialog)
            PlayBotAction.ClickedBackButton -> sendEffect(PlayBotEffect.PopBackStack)
            is PlayBotAction.PieceCaptured -> {
                updateState {
                    val map = state.value.capturedPiece
                    map.increment(action.piece)
                    it.copy(capturedPiece = map)
                }
            }
            is PlayBotAction.Move -> {
                Log.e("tag", action.fen ?: "null")
                updateState {
                    val notation = state.value.notationList.toMutableList()
                    notation.add(action.move)
                    it.copy(notationList = notation, nextMove = null)
                }
                action.fen?.let {
                    viewModelScope.launch {
                        val move = stockfishService.findBestMove(it)
                        updateState { it.copy(nextMove = move) }
                    }
                }
            }
            is PlayBotAction.Result -> {
                val gameResult = when(action.result) {
                    2 -> if(action.whiteSide) GameResult.WIN_CHECKMATE else GameResult.LOSE_CHECKMATE
                    3 -> GameResult.DRAW_STALEMATE
                    4 -> GameResult.DRAW_THREEFOLD_REPETITION
                    5 -> GameResult.DRAW_FIFTY_MOVE_RULE
                    else -> GameResult.DRAW_INSUFFICIENT_MATERIAL
                }
                sendEffect(PlayBotEffect.ShowEndGameDialog(gameResult))
            }
        }
    }
}