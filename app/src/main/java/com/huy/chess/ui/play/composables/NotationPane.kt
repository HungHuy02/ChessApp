package com.huy.chess.ui.play.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NotationPane(
    modifier: Modifier = Modifier,
    notations: List<String>
) {
    LazyRow(modifier = modifier) {
        itemsIndexed(notations) { index, item ->
            NotationPaneItem(
                number = if (index % 2 == 0) index / 2 + 1 else null,
                text = item
            )
        }
    }
}

@Composable
fun NotationPaneItem(
    modifier: Modifier = Modifier,
    number: Int?,
    text: String
) {
    var isClick by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        number?.let {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "${number}.",
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 2.dp,
                    bottom = 6.dp,
                    end = 2.dp
                )
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .background(if (isClick) Color.Gray else Color.White, RoundedCornerShape(2.dp))
                .padding(bottom = 2.dp)
                .background(
                    if (isClick) Color.Cyan else Color.White,
                    RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
                )
                .padding(horizontal = 2.dp, vertical = 4.dp)
                .clickable {
                    isClick = !isClick
                }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NotationPaneItem(
        number = 1,
        text = "a4"
    )
}

@Preview(widthDp = 200)
@Composable
private fun PreviewPane() {
    val strings = listOf("a4", "a5", "a6", "a7", "a8", "a4", "a5", "a6", "a7", "a8")
    NotationPane(notations = strings)
}