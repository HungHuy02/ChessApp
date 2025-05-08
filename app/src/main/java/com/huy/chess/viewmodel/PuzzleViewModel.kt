package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.puzzle.PuzzleAction
import com.huy.chess.ui.puzzle.PuzzleEffect
import com.huy.chess.ui.puzzle.PuzzleState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor() : BaseViewModel<PuzzleState, PuzzleAction, PuzzleEffect>(
    PuzzleState.Default
){
    override fun processAction(action: PuzzleAction) {
        when(action) {
            PuzzleAction.ClickedDailyPuzzle -> sendEffect(PuzzleEffect.NavigateDailyPuzzles)
            PuzzleAction.ClickedPuzzleRush -> sendEffect(PuzzleEffect.NavigateSetupPuzzleRush)
            PuzzleAction.ClickedSolvePuzzles -> sendEffect(PuzzleEffect.NavigateSolvePuzzles)
        }
    }

}