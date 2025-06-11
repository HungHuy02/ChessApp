package com.huy.chess.ui.moreoptions

sealed class MoreOptionsEffect {
    data object NavigateProfile : MoreOptionsEffect()
    data object NavigateSettings : MoreOptionsEffect()
}