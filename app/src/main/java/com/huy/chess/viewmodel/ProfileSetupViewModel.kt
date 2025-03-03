package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.contract.ProfileSetupEvent
import com.huy.chess.contract.ProfileSetupIntent
import com.huy.chess.contract.ProfileSetupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileSetupViewModel @Inject constructor() :
    BaseViewModel<ProfileSetupState, ProfileSetupIntent, ProfileSetupEvent>(
        ProfileSetupState()
    ) {

    override fun processIntent(intent: ProfileSetupIntent) {
        when(intent) {
            ProfileSetupIntent.ClickedButton -> sendEvent(ProfileSetupEvent.NavigateToLogin)
            is ProfileSetupIntent.AvatarPathChanged -> TODO()
            is ProfileSetupIntent.DisplayNameChanged -> updateState { updateDisplayName(it, intent.text) }
        }
    }

    private fun updateDisplayName(state: ProfileSetupState, text: String) : ProfileSetupState {
        return state.copy(
            displayName = text
        )
    }
}