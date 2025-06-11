package com.huy.chess.ui.moreoptions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.moreoptions.composables.OptionItem
import com.huy.chess.viewmodel.MoreOptionsViewModel

@Composable
fun MoreOptionsScreen(
    viewModel: MoreOptionsViewModel = hiltViewModel(),
    navigateSettings: () -> Unit,
    navigateProfile: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                MoreOptionsEffect.NavigateProfile -> navigateProfile()
                MoreOptionsEffect.NavigateSettings -> navigateSettings()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: MoreOptionsState,
    onAction: (MoreOptionsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        OptionItem(
            painter = painterResource(R.drawable.person_24px),
            text = stringResource(R.string.profile_text),
            onClick = { onAction(MoreOptionsAction.ClickedProfile) }
        )
        OptionItem(
            painter = painterResource(R.drawable.settings_24px),
            text = stringResource(R.string.setting_text),
            onClick = { onAction(MoreOptionsAction.ClickedSettings) }
        )
        OptionItem(
            painter = painterResource(R.drawable.logout_24px),
            text = stringResource(R.string.log_out_text),
            onClick = { onAction(MoreOptionsAction.ClickedLogout) }
        )
    }
}