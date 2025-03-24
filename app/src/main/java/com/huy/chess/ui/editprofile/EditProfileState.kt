package com.huy.chess.ui.editprofile

data class EditProfileState(
    val isVerify: Boolean = false,
    val status: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val location: String = ""
)