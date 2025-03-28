package com.huy.chess.ui.puzzlessettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.RowItemWithSwitch
import com.huy.chess.ui.component.RowItemWithSwitchEx
import com.huy.chess.viewmodel.PuzzlesSettingsViewModel

@Composable
fun PuzzlesSettingsScreen(
    viewModel: PuzzlesSettingsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                PuzzlesSettingsEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (PuzzlesSettingsAction) -> Unit
) {
    Column {
        ChessTopAppBar(
            title = "name",
            onClickBack = {}
        )
        RowItemWithSwitchEx(
            label = stringResource(R.string.collect_puzzle_points_text),
            checked = true,
            onCheckedChange = {},
            description = buildAnnotatedString {
                withStyle(SpanStyle()) {
                    appendLine(stringResource(R.string.level_up_with_points_text))
                }
            },
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onAction(PuzzlesSettingsAction.ToggleCollectPoints)
                }
        )
        RowItemWithSwitchEx(
            label = stringResource(R.string.always_show_rating_text),
            checked = true,
            onCheckedChange = {},
            description = buildAnnotatedString {
                withStyle(SpanStyle()) {
                    appendLine(stringResource(R.string.keep_your_rating_in_view_text))
                }
            },
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onAction(PuzzlesSettingsAction.ToggleShowRating)
                }
        )
        RowItemWithSwitch(
            label = stringResource(R.string.show_timer_text),
            checked = true,
            onCheckedChange = {},
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onAction(PuzzlesSettingsAction.ToggleShowTimer)
                }
        )
    }
}