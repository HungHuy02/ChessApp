package com.huy.chess.ui.history

data class HistoryState (
    val pgn: String = "",
    val notationList: List<String> = emptyList(),
    val bottomSide: Boolean = true,
    val bottomName: String = "Bạn",
    val topName: String = "Đối thủ",
    val bottomAvatar: String = "",
    val topAvatar: String = "",
    val showDialog: Boolean = false,
    val currentFen: Int = 0,
    val fen: String? = null,
    val id: String = ""
)