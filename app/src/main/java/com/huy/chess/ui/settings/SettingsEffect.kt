package com.huy.chess.ui.settings

sealed class SettingsEffect {
    data object PopBackStack: SettingsEffect()
    data object NavigateEditProfile: SettingsEffect()
    data object NavigateAccountSettings: SettingsEffect()
    data object NavigateNotificationsSettings: SettingsEffect()
    data object NavigateHomeSettings: SettingsEffect()
    data object NavigateBoardSettings: SettingsEffect()
    data object NavigatePlaySettings: SettingsEffect()
    data object NavigatePuzzlesSettings: SettingsEffect()
    data object NavigateAnalysisSettings: SettingsEffect()
    data object NavigatePrivacySettings: SettingsEffect()
    data object ShowLogOutDialog: SettingsEffect()
}
