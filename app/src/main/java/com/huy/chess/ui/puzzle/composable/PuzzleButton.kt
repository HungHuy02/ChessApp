package com.huy.chess.ui.puzzle.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.theme.Gray700
import com.huy.chess.ui.theme.Gray900
import com.huy.chess.ui.theme.TransparentBlack65

@Composable
fun PuzzleButton(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    AppButton(
        onClick = onClick,
        text = text,
        iconPosition = IconPosition.NEXT_TO_TEXT,
        painter = painter,
        backgroundColor = Gray900,
        backgroundBottomColor = TransparentBlack65,
        backgroundTopColor = Gray700,
        modifier = modifier.fillMaxWidth()
    )
}