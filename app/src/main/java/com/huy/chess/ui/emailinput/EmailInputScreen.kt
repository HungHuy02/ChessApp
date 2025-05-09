package com.huy.chess.ui.emailinput

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.ErrorAlert
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.emailinput.composables.EmailTextField
import com.huy.chess.viewmodel.EmailInputViewModel
import com.huy.chess.viewmodel.RegisterAction
import com.huy.chess.viewmodel.RegisterState
import com.huy.chess.viewmodel.RegisterViewModel

@Composable
fun EmailInputScreen(
    registerViewModel: RegisterViewModel,
    viewModel: EmailInputViewModel = hiltViewModel(),
    navigateToPasswordInput: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val registerState = registerViewModel.state.collectAsState().value
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                EmailInputEffect.NavigateToPasswordInput -> navigateToPasswordInput()
                EmailInputEffect.NavigateLogin -> navigateToLogin()
            }
        }
    }
    Content(registerState, state, registerViewModel::sendAction, viewModel::sendAction)
}

@Composable
private fun Content(
    registerState: RegisterState,
    state: EmailInputState,
    onRegisterAction: (RegisterAction) -> Unit,
    onAction: (EmailInputAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.your_email_text),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        if(state.showNotValid) {
            ErrorAlert(
                text = stringResource(R.string.not_valid_email_text)
            )
        }
        if(state.showAccountExists) {
            ErrorAlert(
                text = stringResource(R.string.account_exists_text),
                action = {
                    Text(
                        text = stringResource(R.string.login_text),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            onAction(EmailInputAction.ClickedLogin)
                        }
                    )
                }
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            value = registerState.email
        ) {
            onAction(EmailInputAction.InputChange)
            onRegisterAction(RegisterAction.EmailChange(it))
        }
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            onClick = { onAction(EmailInputAction.ClickedButton(registerState.email)) },
            text = stringResource(R.string.continue_text),
            iconPosition = IconPosition.NONE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}