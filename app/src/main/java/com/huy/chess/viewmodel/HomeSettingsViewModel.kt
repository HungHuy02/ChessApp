package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.homesettings.HomeSettingsAction
import com.huy.chess.ui.homesettings.HomeSettingsEffect
import com.huy.chess.ui.homesettings.HomeSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeSettingsViewModel @Inject constructor() : BaseViewModel<HomeSettingsState, HomeSettingsAction, HomeSettingsEffect>(HomeSettingsState.Default) {

    override fun processAction(action: HomeSettingsAction) {
        when(action) {
            HomeSettingsAction.ClickedBack -> sendEffect(HomeSettingsEffect.PopBackStack)
            HomeSettingsAction.ToggleShowDailyPuzzles -> TODO()
            HomeSettingsAction.ToggleShowFeaturedEv -> TODO()
            HomeSettingsAction.ToggleShowPlayBots -> TODO()
            HomeSettingsAction.ToggleShowPlayCoach -> TODO()
            HomeSettingsAction.ToggleShowPlayLessons -> TODO()
            HomeSettingsAction.ToggleShowPlayOnline -> TODO()
            HomeSettingsAction.ToggleShowPuzzles -> TODO()
            HomeSettingsAction.ToggleShowRecommendGame -> TODO()
        }
    }
}