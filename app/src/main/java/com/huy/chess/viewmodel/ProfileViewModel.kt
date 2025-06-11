package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.database.repositories.LocalHistoryRepository
import com.huy.chess.data.model.User
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.ui.profile.ProfileAction
import com.huy.chess.ui.profile.ProfileEffect
import com.huy.chess.ui.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localHistoryRepository: LocalHistoryRepository
) : BaseViewModel<ProfileState, ProfileAction, ProfileEffect>(ProfileState()) {

    init {
        viewModelScope.launch {
            combine(
                context.userDataStore.data,
                localHistoryRepository.getHistoriesForPage(1)
            ) { user, histories ->
                Pair(user, histories)
            }.collectLatest { (user, histories) ->
                updateState {
                    it.copy(
                        user = User(
                            name = user.name,
                            email = user.email,
                            avatar = user.avatar,
                            elo = user.elo
                        ),
                        histories = histories
                    )
                }
            }
        }
    }

    override fun processAction(action: ProfileAction) {
        when (action) {
            ProfileAction.ClickedBack -> sendEffect(ProfileEffect.PopBackStack)
            ProfileAction.ClickedEdit -> {
                updateState { it.copy(showMore = false) }
                sendEffect(ProfileEffect.NavigateEditProfile)
            }

            ProfileAction.ClickedFriends -> sendEffect(ProfileEffect.NavigateFriends)
            ProfileAction.ClickedMore -> updateState { it.copy(showMore = !it.showMore) }
            ProfileAction.ClickedQR -> {}
            ProfileAction.ClickedRecentGames -> sendEffect(ProfileEffect.NavigateGameArchive)
            ProfileAction.ClickedShareProfile -> {
                updateState { it.copy(showMore = false) }
            }
        }
    }
}