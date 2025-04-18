package com.huy.chess.ui.editprofile

import com.huy.chess.data.model.User

data class EditProfileState(
    val isVerify: Boolean = false,
    val user: User = User()
)