package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.profilesetup.ProfileSetupEffect
import com.huy.chess.ui.profilesetup.ProfileSetupAction
import com.huy.chess.ui.profilesetup.ProfileSetupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileSetupViewModel @Inject constructor() :
    BaseViewModel<ProfileSetupState, ProfileSetupAction, ProfileSetupEffect>(
        ProfileSetupState()
    ) {

    override fun processAction(action: ProfileSetupAction) {
        when(action) {
            ProfileSetupAction.ClickedButton -> sendEffect(ProfileSetupEffect.NavigateToLogin)
            is ProfileSetupAction.AvatarPathChanged -> TODO()
            is ProfileSetupAction.DisplayNameChanged -> updateState { updateDisplayName(it, action.text) }
        }
    }

    private fun updateDisplayName(state: ProfileSetupState, text: String) : ProfileSetupState {
        return state.copy(
            displayName = text
        )
    }
}