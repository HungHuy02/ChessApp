package com.huy.chess.ui.profilesetup

sealed class ProfileSetupAction {
    data class DisplayNameChanged(val text: String) : ProfileSetupAction()
    data class AvatarPathChanged(val text: String) : ProfileSetupAction()
    data object ClickedButton : ProfileSetupAction()
    data object ClickedChangeAvatar: ProfileSetupAction()
}