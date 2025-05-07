package com.huy.chess.ui.dialog.playonline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R

@Composable
fun PlayOnlineDialog(
    viewModel: PlayOnlineViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateWaiting: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PlayOnlineEffect.NavigateWaiting -> navigateWaiting()
                PlayOnlineEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (PlayOnlineAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 20.sp)) {
                        appendLine(stringResource(R.string.play_online_text))
                    }
                    withStyle(SpanStyle()) {
                        append(stringResource(R.string.random_player))
                    }
                }
            )
            Spacer(Modifier.height(48.dp))
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                TextButton(
                    onClick = { onAction(PlayOnlineAction.ClickedCancel) }
                ) {
                    Text(
                        text = stringResource(R.string.cancel_text),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TextButton(
                    onClick = { onAction(PlayOnlineAction.ClickedStart) }
                ) {
                    Text(
                        text = stringResource(R.string.start_text),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}