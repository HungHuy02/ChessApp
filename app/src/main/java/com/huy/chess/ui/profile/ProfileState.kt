package com.huy.chess.ui.profile

import com.huy.chess.data.database.entities.HistoryEntity
import com.huy.chess.data.model.User

data class ProfileState (
    val user : User = User(),
    val showMore: Boolean = false,
    val histories: List<HistoryEntity> = emptyList()
)