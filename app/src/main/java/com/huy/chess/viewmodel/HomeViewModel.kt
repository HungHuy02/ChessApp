package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.home.HomeAction
import com.huy.chess.ui.home.HomeEffect
import com.huy.chess.ui.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeState, HomeAction, HomeEffect>(HomeState()){
    override fun processAction(action: HomeAction) {
        when(action) {
            HomeAction.ClickedBot -> sendEffect(HomeEffect.NavigatePlayBot)
            HomeAction.ClickedDailyPuzzle -> sendEffect(HomeEffect.NavigateDailyPuzzle)
            HomeAction.ClickedPlayOnline -> sendEffect(HomeEffect.ShowPlayOnlineDialog)
            HomeAction.ClickedPuzzle -> sendEffect(HomeEffect.NavigatePuzzle)
            HomeAction.ClickedStudy -> sendEffect(HomeEffect.NavigateStudy)
            HomeAction.ClickedPlay -> sendEffect(HomeEffect.NavigatePlay)
        }
    }
}