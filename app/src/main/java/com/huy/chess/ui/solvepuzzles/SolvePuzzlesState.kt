package com.huy.chess.ui.solvepuzzles

import com.huy.chess.data.model.Puzzle

data class SolvePuzzlesState (
    val isCorrect: Boolean = false,
    val isFail: Boolean = false,
    val isSuccess: Boolean = false,
    val puzzlesList: List<Puzzle> = emptyList(),
    val currentPuzzleIndex: Int = 0
)