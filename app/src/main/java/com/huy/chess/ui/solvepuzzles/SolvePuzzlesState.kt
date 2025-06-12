package com.huy.chess.ui.solvepuzzles

import com.huy.chess.proto.Puzzle
import com.huy.chess.ui.solvepuzzles.composable.PuzzleDescriptionType

data class SolvePuzzlesState (
    val type: PuzzleDescriptionType = PuzzleDescriptionType.NONE,
    val puzzleId: String = "",
    val rating: Int = 0,
    val puzzle: Puzzle? = null,
    val puzzleMove: List<String> = emptyList(),
    val puzzleStep: Int = 0,
    val isLogin: Boolean = false,
    val userElo: Int = 0,
    val fen: String? = null,
    val nextMove: String? = null,
    val side: Boolean = true,
    val isRePlay: Boolean = false,
    val correctMove: String? = null,
    val showSuggest: Boolean = false,
    val isRetry: Boolean = false
)