package com.huy.chess.ui.editprofile

sealed class EditProfileAction {
    data object ClickedLeft : EditProfileAction()
    data object ClickedDone : EditProfileAction()
    data object ClickedPicture : EditProfileAction()
    data object ClickedFlair : EditProfileAction()
    data object ClickedStatus : EditProfileAction()
    data object ClickedUserName: EditProfileAction()
    data object ClickedFirstName : EditProfileAction()
    data object ClickedLastName : EditProfileAction()
    data object ClickedCountry : EditProfileAction()
    data object ClickedLocation : EditProfileAction()
    data object ClickedLanguage : EditProfileAction()
}