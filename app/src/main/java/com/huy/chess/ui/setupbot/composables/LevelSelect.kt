package com.huy.chess.ui.setupbot.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.huy.chess.utils.enums.StockfishBotLevel

val list = listOf(
    "1" to StockfishBotLevel.ONE,
    "2" to StockfishBotLevel.TWO,
    "3" to StockfishBotLevel.THREE,
    "4" to StockfishBotLevel.FOUR,
    "5" to StockfishBotLevel.FIVE,
    "6" to StockfishBotLevel.SIX,
    "7" to StockfishBotLevel.SEVEN,
    "8" to StockfishBotLevel.EIGHT
)

@Composable
fun LevelSelect(
    modifier: Modifier = Modifier,
    onClick: (StockfishBotLevel) -> Unit,
    level: StockfishBotLevel
) {
    val list = remember { list }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        list.forEach {
            OneLevel(
                text = it.first,
                isSelected = level == it.second
            ) { onClick(it.second) }
        }
    }
}

@Composable
fun OneLevel(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}