package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.database.repositories.LocalHistoryRepository
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.component.pgnToBoard
import com.huy.chess.ui.history.HistoryAction
import com.huy.chess.ui.history.HistoryEffect
import com.huy.chess.ui.history.HistoryState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val localHistoryRepository: LocalHistoryRepository,
    @ApplicationContext private val context: Context
) :
    BaseViewModel<HistoryState, HistoryAction, HistoryEffect>(HistoryState()) {

    init {
        parseFen(Constants.START_FEN)
        viewModelScope.launch {
            context.userDataStore.data.collect {
                updateState { it.copy(id = it.id) }
            }
        }
    }

    override fun processAction(action: HistoryAction) {
        when(action) {
            HistoryAction.ClickedBack -> {
                if(state.value.currentFen > 0)
                    updateState {
                        val index = it.currentFen - 1
                        parseFen(Constants.START_FEN)
                        val pgn = Utils.generatePGNUpTo(it.notationList, index)
                        val fen = pgnToBoard(pgn)
                        it.copy(
                            currentFen = index,
                            fen = fen
                        )
                    }
                else {
                    updateState {
                        it.copy(
                            currentFen = -1,
                            fen = Constants.START_FEN
                        )
                    }
                }
            }
            HistoryAction.ClickedForward -> {
                if(state.value.currentFen < state.value.notationList.size)
                    updateState {
                        val index = it.currentFen + 1
                        parseFen(Constants.START_FEN)
                        val pgn = Utils.generatePGNUpTo(it.notationList, index)
                        val fen = pgnToBoard(pgn)
                        it.copy(
                            currentFen = index,
                            fen = fen
                        )
                    }
            }
            HistoryAction.ClickedMore -> updateState { it.copy(showDialog = true) }
            HistoryAction.ClickedBackButton -> sendEffect(HistoryEffect.PopBackStack)
//            is HistoryAction.Result -> {
//                updateState { it.copy(isEnd = true) }
//                val gameResult = when(action.result) {
//                    2 -> if(action.whiteSide) GameResult.WIN_CHECKMATE else GameResult.LOSE_CHECKMATE
//                    3 -> GameResult.DRAW_STALEMATE
//                    4 -> GameResult.DRAW_THREEFOLD_REPETITION
//                    5 -> GameResult.DRAW_FIFTY_MOVE_RULE
//                    else -> GameResult.DRAW_INSUFFICIENT_MATERIAL
//                }
//                sendEffect(PlayEffect.ShowEndGameDialog(gameResult))
//            }
            HistoryAction.ClickedCopyPgn -> {
                updateState { it.copy(showDialog = false) }
                val list = state.value.notationList
                sendEffect(HistoryEffect.CopyToClipBoard(Utils.generatePGNUpTo(list, list.size)))
            }
            HistoryAction.ClickedRotate -> updateState { it.copy(showDialog = false) }
            HistoryAction.CloseDialog -> updateState { it.copy(showDialog = false) }
            is HistoryAction.ClickedNotation -> {
                val index = action.index + 1
                if(index != state.value.currentFen)
                    updateState {
                        parseFen(Constants.START_FEN)
                        val pgn = Utils.generatePGNUpTo(it.notationList, index)
                        val fen = pgnToBoard(pgn)
                        it.copy(
                            currentFen = index,
                            fen = fen
                        )
                    }
            }
            is HistoryAction.UpdateId -> {
                viewModelScope.launch {
                    localHistoryRepository.getHistoryById(action.id).collect {
                        it.let {entity ->
                            val moves = Utils.extractMovesFromPGN(entity!!.notation)
                            val fen = pgnToBoard(entity.notation)
                            if(state.value.id == entity.ids.split(',')[0])
                                updateState {
                                    it.copy(
                                        fen = fen,
                                        currentFen = moves.size,
                                        notationList = moves,
                                        topName = entity.white,
                                        bottomName = entity.black
                                    )
                                }
                            else
                                updateState {
                                    it.copy(
                                        fen = fen,
                                        currentFen = moves.size,
                                        notationList = moves,
                                        topName = entity.black,
                                        bottomName = entity.white
                                    )
                                }
                        }

                    }
                }
            }
        }
    }
}