package com.huy.chess.contract


data class ProfileSetupState(
    val displayName: String = "",
    val avatarPath: String= "",
    val isButtonEnable: Boolean = false
)

sealed class ProfileSetupIntent {
    data class DisplayNameChanged(val text: String) : ProfileSetupIntent()
    data class AvatarPathChanged(val text: String) : ProfileSetupIntent()
    data object ClickedButton : ProfileSetupIntent()
}

sealed class ProfileSetupEvent {
    data object NavigateToLogin : ProfileSetupEvent ()
}