package com.huy.chess.ui.playbot

data class PlayBotState (
    val notationList: List<String> = emptyList(),
    val capturedPiece: MutableMap<Char, Int> = mutableMapOf(),
    val nextMove: String? = null,
    val listFen: MutableList<String> = mutableListOf()
)