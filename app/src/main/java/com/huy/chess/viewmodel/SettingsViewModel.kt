package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.settings.SettingsAction
import com.huy.chess.ui.settings.SettingsEffect
import com.huy.chess.ui.settings.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel<SettingsState, SettingsAction, SettingsEffect>(SettingsState.Default){

    override fun processAction(action: SettingsAction) {
        when(action) {
            SettingsAction.CLickedAccount -> sendEffect(SettingsEffect.NavigateAccountSettings)
            SettingsAction.ClickedAnalysis -> sendEffect(SettingsEffect.NavigateAnalysisSettings)
            SettingsAction.ClickedBacK -> sendEffect(SettingsEffect.PopBackStack)
            SettingsAction.ClickedBoard -> sendEffect(SettingsEffect.NavigateBoardSettings)
            SettingsAction.ClickedHome -> sendEffect(SettingsEffect.NavigateHomeSettings)
            SettingsAction.ClickedLogOut -> sendEffect(SettingsEffect.ShowLogOutDialog)
            SettingsAction.ClickedNotifications -> sendEffect(SettingsEffect.NavigateNotificationsSettings)
            SettingsAction.ClickedPlay -> sendEffect(SettingsEffect.NavigatePlaySettings)
            SettingsAction.ClickedPrivacySettings -> sendEffect(SettingsEffect.NavigatePrivacySettings)
            SettingsAction.ClickedProfile -> sendEffect(SettingsEffect.NavigateEditProfile)
            SettingsAction.ClickedPuzzles -> sendEffect(SettingsEffect.NavigatePuzzlesSettings)
        }
    }
}