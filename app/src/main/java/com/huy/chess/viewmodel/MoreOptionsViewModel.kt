package com.huy.chess.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.huy.chess.base.BaseViewModel
import com.huy.chess.data.database.repositories.LocalHistoryRepository
import com.huy.chess.data.network.repository.AuthRepository
import com.huy.chess.data.preferences.userDataStore
import com.huy.chess.data.service.DataStoreService
import com.huy.chess.proto.User
import com.huy.chess.ui.moreoptions.MoreOptionsAction
import com.huy.chess.ui.moreoptions.MoreOptionsEffect
import com.huy.chess.ui.moreoptions.MoreOptionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreOptionsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context,
    private val dataStoreService: DataStoreService,
    private val localHistoryRepository: LocalHistoryRepository
) :
    BaseViewModel<MoreOptionsState, MoreOptionsAction, MoreOptionsEffect>(MoreOptionsState()) {

    init {
        viewModelScope.launch {
            context.userDataStore.data.collect {user ->
                updateState { it.copy(isLogin = user.isLogin) }
            }
        }
    }

    override fun processAction(action: MoreOptionsAction) {
        when(action) {
            MoreOptionsAction.ClickedLogout -> {
                viewModelScope.launch {
                    authRepository.logout()
                        .onSuccess {
                            dataStoreService.setAccessToken("".toByteArray())
                            dataStoreService.setRefreshToken("".toByteArray())
                            context.userDataStore.updateData { User.newBuilder().setIsLogin(false).build() }
                            localHistoryRepository.clearAll()
                        }
                }
            }
            MoreOptionsAction.ClickedProfile -> sendEffect(MoreOptionsEffect.NavigateProfile)
            MoreOptionsAction.ClickedSettings -> sendEffect(MoreOptionsEffect.NavigateSettings)
        }
    }
}