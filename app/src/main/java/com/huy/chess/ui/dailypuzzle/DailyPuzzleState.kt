package com.huy.chess.ui.dailypuzzle

import com.huy.chess.utils.enums.PuzzleStatus

data class DailyPuzzleState (
    val date: String = "",
    val puzzleStatus: PuzzleStatus = PuzzleStatus.WRONG_MOVE
)