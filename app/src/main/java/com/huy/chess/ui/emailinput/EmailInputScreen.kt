package com.huy.chess.ui.emailinput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.contract.EmailInputEvent
import com.huy.chess.contract.EmailInputIntent
import com.huy.chess.contract.EmailInputState
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.emailinput.composables.EmailTextField
import com.huy.chess.viewmodel.EmailInputViewModel

@Composable
fun EmailInputScreen(
    viewModel: EmailInputViewModel = hiltViewModel(),
    navigateToPasswordInput: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                EmailInputEvent.NavigateToPasswordInput -> navigateToPasswordInput()
            }
        }
    }
    Content(state, viewModel::sendIntent)
}

@Composable
private fun Content(
    state: EmailInputState,
    onIntent: (EmailInputIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.your_email_text),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.size(16.dp))
        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.inputText
        ) {
            onIntent(EmailInputIntent.InputChanged(it))
        }
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            onClick = { onIntent(EmailInputIntent.ClickedButton) },
            text = stringResource(R.string.continue_text),
            iconPosition = IconPosition.NONE,
            enable = state.isButtonEnable,
            modifier = Modifier.fillMaxWidth()
        )
    }
}