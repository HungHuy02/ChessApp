package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesAction
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesEffect
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SolvePuzzlesViewModel @Inject constructor() : BaseViewModel<SolvePuzzlesState, SolvePuzzlesAction, SolvePuzzlesEffect>(
    SolvePuzzlesState()
){
    override fun processAction(action: SolvePuzzlesAction) {
        when(action) {
            SolvePuzzlesAction.ClickedAnswer -> {}
            SolvePuzzlesAction.ClickedBack -> sendEffect(SolvePuzzlesEffect.PopBackStack)
            SolvePuzzlesAction.ClickedContinue -> {}
            SolvePuzzlesAction.ClickedRetry -> {}
            SolvePuzzlesAction.ClickedSuggestion -> {}
        }
    }

}