package com.huy.chess.ui.editprofile

sealed class EditProfileEffect {
    data object NavigateLanguage : EditProfileEffect()
    data object PopBackStack : EditProfileEffect()
}