package com.huy.chess.ui.analysissettings

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.RowItemWithSwitch
import com.huy.chess.viewmodel.AnalysisSettingsViewModel

fun analysisSettingsList(context: Context): List<Pair<String, AnalysisSettingsAction>> {
    return listOf(
        context.getString(R.string.quick_analysis_text) to AnalysisSettingsAction.ToggleQuickAnalysis,
        context.getString(R.string.show_evaluation_bar_text) to AnalysisSettingsAction.ToggleShowEvaluationBar,
        context.getString(R.string.show_suggestion_arrows_text) to AnalysisSettingsAction.ToggleShowSuggestionArrows,
        context.getString(R.string.show_threat_arrows_text) to AnalysisSettingsAction.ToggleShowThreatArrows,
        context.getString(R.string.show_engine_lines_text) to AnalysisSettingsAction.ToggleShowEngineLines,
        context.getString(R.string.show_move_feedback_text) to AnalysisSettingsAction.ToggleShowMoveFeedback
    )
}

@Composable
fun AnalysisSettingsActionScreen(
    viewModel: AnalysisSettingsViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                AnalysisSettingsEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (AnalysisSettingsAction) -> Unit
) {
    val context = LocalContext.current
    val list = remember { analysisSettingsList(context) }
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
                modifier = Modifier.clickable {
                    onAction(it.second)
                }
            )
        }
    }
}