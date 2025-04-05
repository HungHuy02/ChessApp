package com.huy.chess.ui.dialog.playoptions

sealed class PlayOptionsEffect {
    data object CloseDialog: PlayOptionsEffect()
}