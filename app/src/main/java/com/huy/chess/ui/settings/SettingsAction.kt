package com.huy.chess.ui.settings

sealed class SettingsAction {
    data object ClickedBacK: SettingsAction()
    data object ClickedProfile: SettingsAction()
    data object CLickedAccount: SettingsAction()
    data object ClickedNotifications: SettingsAction()
    data object ClickedHome: SettingsAction()
    data object ClickedBoard: SettingsAction()
    data object ClickedPlay: SettingsAction()
    data object ClickedPuzzles: SettingsAction()
    data object ClickedAnalysis: SettingsAction()
    data object ClickedPrivacySettings: SettingsAction()
    data object ClickedLogOut: SettingsAction()
}