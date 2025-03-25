package com.huy.chess.viewmodel

import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.network.repository.AuthRepository
import com.huy.chess.model.request.RegisterRequest
import com.huy.chess.ui.profilesetup.ProfileSetupEffect
import com.huy.chess.ui.profilesetup.ProfileSetupAction
import com.huy.chess.ui.profilesetup.ProfileSetupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSetupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :
    BaseViewModel<ProfileSetupState, ProfileSetupAction, ProfileSetupEffect>(
        ProfileSetupState()
    ) {

    override fun processAction(action: ProfileSetupAction) {
        when(action) {
            is ProfileSetupAction.ClickedButton -> handleRegister(action.email, action.password, action.username, action.avatar)
            is ProfileSetupAction.AvatarPathChanged -> updateState { updateAvatarPath(it, action.text) }
            is ProfileSetupAction.DisplayNameChanged -> updateState { updateDisplayName(it, action.text) }
            ProfileSetupAction.ClickedChangeAvatar -> sendEffect(ProfileSetupEffect.OpenImagePicker)
        }
    }

    private fun handleRegister(email: String, password: String, username: String, avatar: String?) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(username, email, password, avatar)
            authRepository.register(registerRequest)
                .onSuccess {
                    sendEffect(ProfileSetupEffect.NavigateToLogin)
                }
                .onFailure {

                }
        }
    }

    private fun updateDisplayName(state: ProfileSetupState, text: String) : ProfileSetupState {
        return state.copy(
            isButtonEnable = true
        )
    }

    private fun updateAvatarPath(state: ProfileSetupState, path: String) : ProfileSetupState {
        return state.copy(
            isButtonEnable = true
        )
    }
}