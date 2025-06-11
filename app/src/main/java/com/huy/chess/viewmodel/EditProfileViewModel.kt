package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.model.User
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.editprofile.EditProfileAction
import com.huy.chess.ui.editprofile.EditProfileEffect
import com.huy.chess.ui.editprofile.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) :
    BaseViewModel<EditProfileState, EditProfileAction, EditProfileEffect>(EditProfileState())
{
    init {
        viewModelScope.launch {
            context.userDataStore.data.collect { user ->
                updateState { it.copy(user = User(
                    name = user.name,
                    email = user.email,
                    avatar = user.avatar,
                    elo = user.elo
                )) }
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