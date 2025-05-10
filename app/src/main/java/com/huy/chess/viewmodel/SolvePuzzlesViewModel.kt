package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.model.Puzzle
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesAction
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesEffect
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesState
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescriptionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolvePuzzlesViewModel @Inject constructor() : BaseViewModel<SolvePuzzlesState, SolvePuzzlesAction, SolvePuzzlesEffect>(
    SolvePuzzlesState()
){
    init {
        viewModelScope.launch {
            updateState { it.copy(puzzlesList = listOf(
                Puzzle("r6k/pp2r2p/4Rp1Q/3p4/8/1N1P2R1/PqP2bPP/7K b - - 0 24", "f2g3 e6e7 b2b1 b3c1 b1c1 h6c1"),
                Puzzle("5rk1/1p3ppp/pq3b2/8/8/1P1Q1N2/P4PPP/3R2K1 w - - 2 27", "d3d6 f8d8 d6d8 f6d8")
            ))
            }
            val puzzle = state.value.puzzlesList[0]
            val side = parseFen(puzzle.fen)
            updateState { it.copy(
                puzzleMove = puzzle.moves.split(" "),
                type = if(side) PuzzleDescriptionType.WhiteMove else PuzzleDescriptionType.BlackMove
            ) }
        }
    }

    override fun processAction(action: SolvePuzzlesAction) {
        when(action) {
            SolvePuzzlesAction.ClickedAnswer -> {}
            SolvePuzzlesAction.ClickedBack -> sendEffect(SolvePuzzlesEffect.PopBackStack)
            SolvePuzzlesAction.ClickedContinue -> {
                updateState { it.copy(currentPuzzleIndex = it.currentPuzzleIndex + 1) }
            }
            SolvePuzzlesAction.ClickedRetry -> {}
            SolvePuzzlesAction.ClickedSuggestion -> {}
            is SolvePuzzlesAction.Move -> {
                val value = state.value
                if(action.move == value.puzzleMove[value.puzzleStep]) {
                    if(value.puzzleStep == value.puzzleMove.size - 1) {
                        updateState { it.copy(type = PuzzleDescriptionType.Correct) }
                    } else {
                        updateState { it.copy(puzzleStep = it.puzzleStep + 1) }
                    }
                } else {
                    updateState { it.copy(type = PuzzleDescriptionType.Fail) }
                }
            }
        }
    }

}