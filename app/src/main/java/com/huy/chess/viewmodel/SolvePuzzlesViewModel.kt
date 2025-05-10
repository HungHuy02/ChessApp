package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.component.parseFen
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesAction
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesEffect
import com.huy.chess.ui.solvepuzzles.SolvePuzzlesState
import com.huy.chess.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolvePuzzlesViewModel @Inject constructor() : BaseViewModel<SolvePuzzlesState, SolvePuzzlesAction, SolvePuzzlesEffect>(
    SolvePuzzlesState()
){
    init {
        parseFen("r3r1k1/5ppp/2p5/p2p1bP1/P2Q3P/2P5/2KPq1B1/R1B5 w - - 3 23")
    }

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