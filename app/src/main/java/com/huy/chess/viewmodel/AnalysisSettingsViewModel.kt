package com.huy.chess.viewmodel

import com.huy.chess.base.BaseViewModel
import com.huy.chess.ui.analysissettings.AnalysisSettingsAction
import com.huy.chess.ui.analysissettings.AnalysisSettingsEffect
import com.huy.chess.ui.analysissettings.AnalysisSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalysisSettingsViewModel @Inject constructor() : BaseViewModel<AnalysisSettingsState, AnalysisSettingsAction, AnalysisSettingsEffect>(AnalysisSettingsState.Default) {
    override fun processAction(action: AnalysisSettingsAction) {
        when(action) {
            AnalysisSettingsAction.ClickedBack -> sendEffect(AnalysisSettingsEffect.PopBackStack)
            AnalysisSettingsAction.ToggleQuickAnalysis -> TODO()
            AnalysisSettingsAction.ToggleShowEngineLines -> TODO()
            AnalysisSettingsAction.ToggleShowEvaluationBar -> TODO()
            AnalysisSettingsAction.ToggleShowMoveFeedback -> TODO()
            AnalysisSettingsAction.ToggleShowSuggestionArrows -> TODO()
            AnalysisSettingsAction.ToggleShowThreatArrows -> TODO()
        }
    }
}