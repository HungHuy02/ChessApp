package com.huy.chess.ui.play

data class PlayState (
    val notationList: List<String> = emptyList(),
    val capturedPiece: MutableMap<Char, Int> = mutableMapOf(),
    val bottomSide: Boolean = true,
    val bottomName: String = "",
    val topName: String = "",
    val bottomAvatar: String = "",
    val topAvatar: String = "",
    val autoRotate: Boolean = false,
    val isEnd: Boolean = false,
    val showDialog: Boolean = false
)