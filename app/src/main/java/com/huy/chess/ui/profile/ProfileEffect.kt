package com.huy.chess.ui.profile

sealed class ProfileEffect {
    data object PopBackStack: ProfileEffect()
    data object NavigateEditProfile: ProfileEffect()
    data object NavigateGameArchive: ProfileEffect()
    data object NavigateFriends: ProfileEffect()
}