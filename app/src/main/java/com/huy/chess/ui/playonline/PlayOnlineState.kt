package com.huy.chess.ui.playonline

import com.huy.chess.data.model.OnlinePlayer

data class PlayOnlineState (
    val notationList: List<String> = emptyList(),
    val capturedPiece: MutableMap<Char, Int> = mutableMapOf(),
    val nextMove: String? = null,
    val listFen: MutableList<String> = mutableListOf(),
    val side: Boolean = false,
    val player1: OnlinePlayer = OnlinePlayer("" ,"You", 800, ""),
    val player2: OnlinePlayer = OnlinePlayer("","Opponent", 800, ""),
)