package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.component.pgnToBoard
import com.huy.chess.ui.history.HistoryAction
import com.huy.chess.ui.history.HistoryEffect
import com.huy.chess.ui.history.HistoryState
import com.huy.chess.utils.Constants
import com.huy.chess.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() :
    BaseViewModel<HistoryState, HistoryAction, HistoryEffect>(HistoryState()) {

    init {
        parseFen(Constants.START_FEN)
        val pgn = "1. f4 d6 2. Nf3 g5 3. d4 g4 4. Ng5 a6 5. e3 f5 6. Bc4 b6 7. Bxg8 Rxg8 8. O-O h6 9. e4 hxg5 10. fxg5 Rh8 11. exf5 b5 12. Qxg4 e5 13. dxe5 Be7 14. exd6 Qxd6 15. Bf4 Qc5+ 16. Rf2 Rf8 17. Nd2 Rf6 18. gxf6 Bf8 19. Ne4 Ra7 20. Nxc5 Bxc5 21. Kf1 Bb7 22. Ke2 Nc6 23. c3 Nb8 24. b4 Nd7 25. g3 Bxb4 26. cxb4"
        val moves = Utils.extractMovesFromPGN(pgn)
        val fen = pgnToBoard(pgn)
        viewModelScope.launch {
            updateState {
                it.copy(
                    fen = fen,
                    currentFen = moves.size,
                    notationList = moves
                )
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
        }
    }
}