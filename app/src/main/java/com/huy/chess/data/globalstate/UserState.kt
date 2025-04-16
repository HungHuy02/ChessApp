package com.huy.chess.data.globalstate

import com.huy.chess.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserState @Inject constructor() {
    private var _state: MutableStateFlow<User> = MutableStateFlow(User())
    val state: StateFlow<User> = _state.asStateFlow()

    fun updateUser(
        name: String? = null,
        email: String? = null,
        avatar: String? = null
    ) {
        _state.update {
            it.copy(
                name = name ?: it.name,
                email = email ?: it.email,
                avatar = avatar ?: it.avatar
            )
        }
    }
}