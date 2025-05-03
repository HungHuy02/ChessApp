package com.huy.chess.utils.enums

import androidx.annotation.StringRes

data class GameResultInfo(
    val resultNotation: String,
    @StringRes val result: Int,
    @StringRes val dec: Int
)

enum class GameResult {
    // Win
    WIN_CHECKMATE,
    WIN_OPPONENT_RESIGNED,
    WIN_OPPONENT_FORFEIT,
    WIN_TIMEOUT,
//    WIN_ILLEGAL_MOVE,

    // Lose
    LOSE_CHECKMATE,
    LOSE_RESIGNED,
    LOSE_FORFEIT,
    LOSE_TIMEOUT,
//    LOSE_ILLEGAL_MOVE,

    // Draw
    DRAW_STALEMATE,
    DRAW_THREEFOLD_REPETITION,
    DRAW_FIFTY_MOVE_RULE,
    DRAW_INSUFFICIENT_MATERIAL,
    DRAW_AGREEMENT,
    DRAW_TIMEOUT_INSUFFICIENT_MATERIAL
}