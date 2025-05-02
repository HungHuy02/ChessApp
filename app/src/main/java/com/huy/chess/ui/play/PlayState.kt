package com.huy.chess.ui.play

data class PlayState (
    val notationList: List<String> = emptyList(),
    val capturedPiece: MutableMap<Char, Int> = mutableMapOf()
)