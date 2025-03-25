package com.huy.chess.ui.profilesetup

sealed class ProfileSetupAction {
    data class DisplayNameChanged(val text: String) : ProfileSetupAction()
    data class AvatarPathChanged(val text: String) : ProfileSetupAction()
    data class ClickedButton(val email: String, val password: String, val username: String, val avatar: String?) : ProfileSetupAction()
    data object ClickedChangeAvatar: ProfileSetupAction()
}