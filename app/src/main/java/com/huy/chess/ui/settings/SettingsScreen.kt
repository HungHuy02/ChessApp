package com.huy.chess.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.settings.composable.settingsRowList
import com.huy.chess.viewmodel.SettingsViewModel
import com.huy.chess.R
import com.huy.chess.ui.settings.composable.SettingsRow

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateAccountSettings: () -> Unit,
    navigateAnalysisSettings: () -> Unit,
    navigateBoardSettings: () -> Unit,
    navigateEditProfile: () -> Unit,
    navigateHomeSettings: () -> Unit,
    navigateNotificationsSettings: () -> Unit,
    navigatePlaySettings: () -> Unit,
    navigatePrivacySettings: () -> Unit,
    navigatePuzzlesSettings: () -> Unit,
    showLogOutDialog: () -> Unit,
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                SettingsEffect.PopBackStack -> popBackStack()
                SettingsEffect.NavigateAccountSettings -> navigateAccountSettings()
                SettingsEffect.NavigateAnalysisSettings -> navigateAnalysisSettings()
                SettingsEffect.NavigateBoardSettings -> navigateBoardSettings()
                SettingsEffect.NavigateEditProfile -> navigateEditProfile()
                SettingsEffect.NavigateHomeSettings -> navigateHomeSettings()
                SettingsEffect.NavigateNotificationsSettings -> navigateNotificationsSettings()
                SettingsEffect.NavigatePlaySettings -> navigatePlaySettings()
                SettingsEffect.NavigatePrivacySettings -> navigatePrivacySettings()
                SettingsEffect.NavigatePuzzlesSettings -> navigatePuzzlesSettings()
                SettingsEffect.ShowLogOutDialog -> showLogOutDialog()
            }
        }
    }
    Content(viewModel::sendAction)
}

@Composable
private fun Content(
    onAction: (SettingsAction) -> Unit
) {
    val context = LocalContext.current
    val list = remember { settingsRowList(context) }
    Column {
        ChessTopAppBar(
            title = stringResource(R.string.setting_text),
            onClickBack = { onAction(SettingsAction.ClickedBacK) }
        )
        list.forEach {
            SettingsRow(
                imageBitmap = it.imageBitmap,
                label = it.label,
                onClick = { onAction(it.action) }
            )
        }
    }
}