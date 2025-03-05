package com.huy.chess.ui.profilesetup

sealed class ProfileSetupEffect {
    data object NavigateToLogin : ProfileSetupEffect()
}