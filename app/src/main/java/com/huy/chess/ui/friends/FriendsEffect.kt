package com.huy.chess.ui.friends

sealed class FriendsEffect {
    data object PopBackStack: FriendsEffect()
}