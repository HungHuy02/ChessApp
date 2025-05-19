package com.huy.chess.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.huy.chess.ui.theme.TransparentBlack10

@Composable
fun NotationPane(
    modifier: Modifier = Modifier,
    notations: List<String>,
    currentNotation: Int = 0,
    onClick: (Int) -> Unit = {}
) {
    val state = rememberLazyListState()
    LaunchedEffect(currentNotation) {
        if (currentNotation > 0)
            if(notations.isNotEmpty())
                state.animateScrollToItem(currentNotation)
    }
    LazyRow(
        modifier = modifier.fillMaxWidth().height(32.dp)
            .background(MaterialTheme.colorScheme.surface),
        state = state,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(start = 4.dp)
    ) {
        itemsIndexed(notations) { index, item ->
            NotationPaneItem(
                number = if (index % 2 == 0) index / 2 + 1 else null,
                text = item,
                selected = index == currentNotation,
                onClick = { onClick(index) }
            )
        }
    }
}

@Composable
fun NotationPaneItem(
    modifier: Modifier = Modifier,
    number: Int?,
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        number?.let {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "${number}.",
                color = Color.Gray,
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 2.dp,
                    bottom = 6.dp,
                    end = 2.dp
                )
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .background(if (selected) Color.Gray else Color.Unspecified, RoundedCornerShape(2.dp))
                .padding(bottom = 2.dp)
                .background(
                    if (selected) TransparentBlack10 else Color.Unspecified,
                    RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)
                )
                .padding(horizontal = 2.dp, vertical = 4.dp)
                .clickable {
                    onClick()
                }
        )
    }
}