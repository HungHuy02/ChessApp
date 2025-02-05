package com.huy.chess.ui.newgame.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.huy.chess.R
import com.huy.chess.designsystem.AppButton
import com.huy.chess.designsystem.IconPosition

@Composable
fun SameButton(
    onClick: () -> Unit,
    text: String,
    painter: Painter
) {
    AppButton(
        onClick = onClick,
        text = text,
        textStyle = MaterialTheme.typography.titleSmall,
        textColor = MaterialTheme.colorScheme.onBackground,
        painter = painter,
        iconPosition = IconPosition.NEXT_TO_TEXT,
        modifier = Modifier.fillMaxWidth()
    )
}