package com.huy.chess.ui.home

import com.huy.chess.utils.Constants

data class HomeState(
    val isLogin: Boolean = false,
    val onlineFen: String = Constants.START_FEN,
    val puzzleFen: String = "",
    val dailyPuzzleFen: String = "",
    val totalSolved: Int = 0,
    val botFen: String = Constants.START_FEN
)