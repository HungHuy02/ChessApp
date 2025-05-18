package com.huy.chess.ui.play.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.huy.chess.ui.play.PlayAction
import com.huy.chess.R

@Composable
fun OptionsDialog(
    onDismiss: () -> Unit,
    enableRotate: Boolean,
    onClick: (PlayAction) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)
                .border(width = 0.5.dp, color = Color.Gray, shape = MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
        ) {
            RowItem(
                text = stringResource(R.string.accept_defeat_text),
                onClick = { onClick(PlayAction.ClickedSurrender) }
            )
            RowItem(
                text = stringResource(if(enableRotate) R.string.disable_board_rotation_text else R.string.enable_board_rotation_text),
                onClick = { onClick(PlayAction.ClickedRotate) }
            )
            RowItem(
                text = stringResource(R.string.copy_pgn_text),
                onClick = { onClick(PlayAction.ClickedCopyPgn) }
            )
        }
    }
}

@Composable
private fun RowItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier.fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp)
    )
}