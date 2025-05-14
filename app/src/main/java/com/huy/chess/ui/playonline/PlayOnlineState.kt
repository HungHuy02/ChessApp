package com.huy.chess.ui.playonline

data class PlayOnlineState (
    val notationList: List<String> = emptyList(),
    val capturedPiece: MutableMap<Char, Int> = mutableMapOf(),
    val nextMove: String? = null,
    val listFen: MutableList<String> = mutableListOf()
)