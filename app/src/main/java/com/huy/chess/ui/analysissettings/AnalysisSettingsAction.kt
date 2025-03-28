package com.huy.chess.ui.analysissettings

sealed class AnalysisSettingsAction {
    data object ClickedBack: AnalysisSettingsAction()
    data object ToggleQuickAnalysis: AnalysisSettingsAction()
    data object ToggleShowEvaluationBar: AnalysisSettingsAction()
    data object ToggleShowSuggestionArrows: AnalysisSettingsAction()
    data object ToggleShowThreatArrows: AnalysisSettingsAction()
    data object ToggleShowEngineLines: AnalysisSettingsAction()
    data object ToggleShowMoveFeedback: AnalysisSettingsAction()
}