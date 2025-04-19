package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.dailypuzzle.DailyPuzzleAction
import com.huy.chess.ui.dailypuzzle.DailyPuzzleEffect
import com.huy.chess.ui.dailypuzzle.DailyPuzzleState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyPuzzleViewModel @Inject constructor() : BaseViewModel<DailyPuzzleState, DailyPuzzleAction, DailyPuzzleEffect>(DailyPuzzleState()) {
    override fun processAction(action: DailyPuzzleAction) {
        when(action) {
            DailyPuzzleAction.ClickedAnalysis -> {}
            DailyPuzzleAction.ClickedBack -> {}
            DailyPuzzleAction.ClickedBackButton -> sendEffect(DailyPuzzleEffect.PopBackStack)
            DailyPuzzleAction.ClickedForward -> {}
            DailyPuzzleAction.ClickedHint -> {}
            DailyPuzzleAction.ClickedReset -> {}
            DailyPuzzleAction.ClickedDate -> sendEffect(DailyPuzzleEffect.NavigateSelectDate)
        }
    }
}