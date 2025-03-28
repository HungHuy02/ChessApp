package com.huy.chess.ui.analysissettings

sealed class AnalysisSettingsEffect {
    data object PopBackStack : AnalysisSettingsEffect()
}