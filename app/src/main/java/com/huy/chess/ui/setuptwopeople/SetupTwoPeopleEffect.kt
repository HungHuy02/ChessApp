package com.huy.chess.ui.setuptwopeople

import com.huy.chess.utils.enums.TimeType

sealed class SetupTwoPeopleEffect {
    data object PopBackStack: SetupTwoPeopleEffect()
    data class NavigatePlay(val whiteName: String, val blackName: String, val time: TimeType, val enableRotate: Boolean): SetupTwoPeopleEffect()
}