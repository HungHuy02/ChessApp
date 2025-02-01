package com.huy.chess.ui.home.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.designsystem.RenderChessBoardFromFEN

@Composable
fun HomeItems(fen: String, title: String, description: String, @DrawableRes icon: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        RenderChessBoardFromFEN(fen = fen, size = 100.dp)
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(title)
            Text(description)
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HomeItems(
                "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                "Chơi trực tuyen",
                "10 phut vơới ối th",
                R.drawable.bknight
            )
        }
        item {
            HomeItems(
                "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                "Chơi trực tuyen",
                "10 phut vơới ối th",
                R.drawable.bbishop
            )
        }
        item {
            HomeItems(
                "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                "Chơi trực tuyen",
                "10 phut vơới ối th",
                R.drawable.bpawn
            )
        }
    }
}