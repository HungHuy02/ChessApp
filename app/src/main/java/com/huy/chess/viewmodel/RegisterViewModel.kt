package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val avatarPath: String = ""
)

sealed class RegisterAction {
    data class EmailChange(val email: String) : RegisterAction()
    data class PasswordChange(val password: String) : RegisterAction()
    data class NameChange(val name: String) : RegisterAction()
    data class AvatarPathChange(val avatarPath: String) : RegisterAction()
}

@HiltViewModel
class RegisterViewModel @Inject constructor() :
    BaseViewModel<RegisterState, RegisterAction, Unit>(RegisterState()) {

    override fun processAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.AvatarPathChange -> updateState {
                updateAvatarPath(it,action.avatarPath)
            }
            is RegisterAction.EmailChange -> updateState {
                updateEmail(it, action.email)
            }
            is RegisterAction.NameChange -> updateState {
                updateName(it, action.name)
            }
            is RegisterAction.PasswordChange -> updateState {
                updatePassword(it, action.password)
            }
        }
    }

    private fun updateEmail(state: RegisterState, email: String): RegisterState {
        return state.copy(email = email)
    }

    private fun updatePassword(state: RegisterState, password: String): RegisterState {
        return state.copy(password = password)
    }

    private fun updateName(state: RegisterState, name: String): RegisterState {
        return state.copy(name = name)
    }

    private fun updateAvatarPath(state: RegisterState, avatarPath: String): RegisterState {
        return state.copy(avatarPath = avatarPath)
    }
}