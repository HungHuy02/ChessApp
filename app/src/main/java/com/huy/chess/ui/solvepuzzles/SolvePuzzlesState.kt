package com.huy.chess.ui.solvepuzzles

import com.huy.chess.data.model.Puzzle
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescriptionType

data class SolvePuzzlesState (
    val type: PuzzleDescriptionType = PuzzleDescriptionType.WhiteMove,
    val puzzlesList: List<Puzzle> = emptyList(),
    val puzzleMove: List<String> = emptyList(),
    val puzzleStep: Int = 0,
    val currentPuzzleIndex: Int = 0
)