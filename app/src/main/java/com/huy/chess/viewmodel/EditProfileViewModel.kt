package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.editprofile.EditProfileAction
import com.huy.chess.ui.editprofile.EditProfileEffect
import com.huy.chess.ui.editprofile.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() :
    BaseViewModel<EditProfileState, EditProfileAction, EditProfileEffect>(EditProfileState()) {
    override fun processAction(action: EditProfileAction) {
        when(action) {
            EditProfileAction.ClickedCountry -> {}
            EditProfileAction.ClickedDone -> {}
            EditProfileAction.ClickedFirstName -> {}
            EditProfileAction.ClickedFlair -> {}
            EditProfileAction.ClickedLanguage -> {}
            EditProfileAction.ClickedLastName -> {}
            EditProfileAction.ClickedLeft -> {
                if (state.value.isVerify) updateState { it.copy(isVerify = false) }
                else sendEffect(EditProfileEffect.PopBackStack)
            }
            EditProfileAction.ClickedPicture -> {}
            EditProfileAction.ClickedStatus -> {}
            EditProfileAction.ClickedUserName -> {}
            EditProfileAction.ClickedLocation -> {}
        }
    }
}