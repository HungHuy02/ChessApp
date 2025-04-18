package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.globalstate.UserState
import com.huy.chess.ui.editprofile.EditProfileAction
import com.huy.chess.ui.editprofile.EditProfileEffect
import com.huy.chess.ui.editprofile.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userState: UserState
) :
    BaseViewModel<EditProfileState, EditProfileAction, EditProfileEffect>(EditProfileState())
{
    init {
        viewModelScope.launch {
            userState.state.collect { user ->
                updateState { it.copy(user = user) }
            }
        }
    }

    override fun processAction(action: EditProfileAction) {
        when(action) {
            EditProfileAction.ClickedCountry -> {}
            EditProfileAction.ClickedDone -> {}
            EditProfileAction.ClickedFirstName -> {}
            EditProfileAction.ClickedFlair -> {}
            EditProfileAction.ClickedLanguage -> { sendEffect(EditProfileEffect.NavigateLanguage) }
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