package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.preferences.dailyPuzzleDataStore
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.dailypuzzle.DailyPuzzleAction
import com.huy.chess.ui.dailypuzzle.DailyPuzzleEffect
import com.huy.chess.ui.dailypuzzle.DailyPuzzleState
import com.huy.chess.utils.enums.PuzzleStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyPuzzleViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel<DailyPuzzleState, DailyPuzzleAction, DailyPuzzleEffect>(DailyPuzzleState()) {

    init {
        viewModelScope.launch {
            context.dailyPuzzleDataStore.data.collect {puzzle ->
                val side = parseFen(puzzle.fen)
                updateState { it.copy(
                    title = puzzle.title,
                    date = puzzle.date,
                    fen = puzzle.fen,
                    moves = puzzle.moves.split(" "),
                    puzzleStatus = if(side) PuzzleStatus.START_WHITE else PuzzleStatus.START_BLACK
                ) }
            }
        }
    }

    override fun processAction(action: DailyPuzzleAction) {
        when(action) {
            DailyPuzzleAction.ClickedAnalysis -> {}
            DailyPuzzleAction.ClickedBack -> {}
            DailyPuzzleAction.ClickedBackButton -> sendEffect(DailyPuzzleEffect.PopBackStack)
            DailyPuzzleAction.ClickedForward -> {}
            DailyPuzzleAction.ClickedHint -> {}
            DailyPuzzleAction.ClickedReset -> {}
            DailyPuzzleAction.ClickedDate -> sendEffect(DailyPuzzleEffect.NavigateSelectDate)
            is DailyPuzzleAction.Move -> {
                val value = state.value
                if(action.move == value.moves[value.puzzleStep]) {
                    if(value.puzzleStep == value.moves.size - 1) {
                        updateState { it.copy(puzzleStatus = PuzzleStatus.FINISH) }
                    } else {
                        updateState { it.copy(
                            puzzleStep = it.puzzleStep + 2,
                            puzzleStatus = PuzzleStatus.CORRECT_MOVE,
                            nextMove = it.moves[it.puzzleStep + 1]
                        ) }
                    }
                } else {
                    updateState { it.copy(puzzleStatus = PuzzleStatus.WRONG_MOVE) }
                }
            }
        }
    }
}