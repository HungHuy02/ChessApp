package com.huy.chess.ui.boardsettings

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.RowItemWithSwitch
import com.huy.chess.ui.homesettings.HomeSettingsAction
import com.huy.chess.viewmodel.BoardSettingsViewModel
import kotlinx.coroutines.flow.collect

fun boardSettingsList(context: Context): List<Pair<String, BoardSettingsAction>> {
    return listOf(
        context.getString(R.string.sounds_text) to BoardSettingsAction.ToggleSounds,
        context.getString(R.string.haptic_feedback_text) to BoardSettingsAction.ToggleHapticFeedback,
        context.getString(R.string.show_coordinates_text) to BoardSettingsAction.ToggleShowCoordinates,
        context.getString(R.string.highlight_last_move_text) to BoardSettingsAction.ToggleHighlightLastMove,
        context.getString(R.string.show_legal_moves_text) to BoardSettingsAction.ToggleShowLegalMove,
        context.getString(R.string.magnify_pieces_text) to BoardSettingsAction.ToggleMagnifyPieces,
        context.getString(R.string.game_result_board_animations_text) to BoardSettingsAction.ToggleGameResultBoardAnimations
    )
}

@Composable
fun BoardSettingsScreen(
    viewModel: BoardSettingsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                BoardSettingsEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (BoardSettingsAction) -> Unit
) {
    val context = LocalContext.current
    val list = remember { boardSettingsList(context) }
    Column {
        ChessTopAppBar(
            title = "name",
            onClickBack = {}
        )
        list.forEach {
            RowItemWithSwitch(
                label = it.first,
                checked = false,
                onCheckedChange = {},
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        onAction(it.second)
                    }
            )
        }
    }
}