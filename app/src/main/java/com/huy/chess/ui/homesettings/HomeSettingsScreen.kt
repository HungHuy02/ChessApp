package com.huy.chess.ui.homesettings

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
import com.huy.chess.viewmodel.HomeSettingsViewModel

fun homeSettingsList(context: Context): List<Pair<String, HomeSettingsAction>> {
    return listOf(
        context.getString(R.string.show_featured_events_text) to HomeSettingsAction.ToggleShowFeaturedEv,
        context.getString(R.string.show_recommended_games_text) to HomeSettingsAction.ToggleShowRecommendGame,
        context.getString(R.string.show_play_online_text) to HomeSettingsAction.ToggleShowPlayOnline,
        context.getString(R.string.show_play_bots_text) to HomeSettingsAction.ToggleShowPlayBots,
        context.getString(R.string.show_play_coach_text) to HomeSettingsAction.ToggleShowPlayCoach,
        context.getString(R.string.show_lessons_text) to HomeSettingsAction.ToggleShowPlayLessons,
        context.getString(R.string.show_puzzles_text) to HomeSettingsAction.ToggleShowPuzzles,
        context.getString(R.string.show_daily_puzzle_text) to HomeSettingsAction.ToggleShowDailyPuzzles
    )
}

@Composable
fun HomeSettingsScreen(
    viewModel: HomeSettingsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                HomeSettingsEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (HomeSettingsAction) -> Unit
) {
    val context = LocalContext.current
    val list = remember { homeSettingsList(context) }
    Column {
        ChessTopAppBar(
            title = "name",
            onClickBack = {}
        )
        list.forEach {
            RowItemWithSwitch(
                label = it.first,
                checked = true,
                onCheckedChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAction(it.second) }
            )
        }
    }
}