package com.huy.chess.ui.play

import com.huy.chess.utils.Constants

data class PlayState (
    val notationList: List<String> = emptyList(),
    val capturedPiece: MutableMap<Char, Int> = mutableMapOf(),
    val bottomSide: Boolean = true,
    val bottomName: String = "Bạn",
    val topName: String = "Đối thủ",
    val bottomAvatar: String = "",
    val topAvatar: String = "",
    val autoRotate: Boolean = false,
    val isEnd: Boolean = false,
    val showDialog: Boolean = false,
    val listFen: MutableList<String> = mutableListOf(Constants.START_FEN),
    val currentFen: Int = 0,
    val displayFen: String? = null,

)