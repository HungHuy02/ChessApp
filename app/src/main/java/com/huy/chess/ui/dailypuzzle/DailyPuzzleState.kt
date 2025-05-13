package com.huy.chess.ui.dailypuzzle

import com.huy.chess.utils.enums.PuzzleStatus

data class DailyPuzzleState (
    val date: String = "",
    val title: String = "",
    val fen: String? = null,
    val moves: List<String> = emptyList(),
    val nextMove: String? = null,
    val puzzleStep: Int = 0,
    val puzzleStatus: PuzzleStatus = PuzzleStatus.START_WHITE
)