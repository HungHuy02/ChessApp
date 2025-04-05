package com.huy.chess.ui.dialog.playoptions

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.dialog.playoptions.composable.PlayOptionsRow

@Composable
fun PlayOptionsDialog(
    viewModel: PlayOptionsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PlayOptionsEffect.CloseDialog -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
fun Content(onAction: (PlayOptionsAction) -> Unit) {
    val list = listOf(
        R.string.flip_board_text to PlayOptionsAction.ClickedFlip,
        R.string.explorer_text to PlayOptionsAction.ClickedExplorer,
        R.string.share_game_text to PlayOptionsAction.ClickedShare,
        R.string.save_game_text to PlayOptionsAction.ClickedSave
    )
    Column(
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.more_options_text)
            )
            Icon(
                painter = painterResource(R.drawable.close_24px),
                contentDescription = "close icon",
                modifier = Modifier
                    .clickable {
                        onAction(PlayOptionsAction.ClickedClose)
                    }
            )
        }
        list.forEach {
            PlayOptionsRow(
                textId = it.first,
            ) {
                onAction(it.second)
            }
        }

    }
}
