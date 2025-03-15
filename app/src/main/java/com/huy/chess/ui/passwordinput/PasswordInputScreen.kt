package com.huy.chess.ui.passwordinput

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
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.PasswordTextField
import com.huy.chess.viewmodel.PasswordInputViewModel
import com.huy.chess.viewmodel.RegisterAction
import com.huy.chess.viewmodel.RegisterState
import com.huy.chess.viewmodel.RegisterViewModel

@Composable
fun PasswordInputScreen(
    registerViewModel: RegisterViewModel,
    viewModel: PasswordInputViewModel = hiltViewModel(),
    navigateToProfileSetup: () -> Unit
) {
    val registerState = registerViewModel.state.collectAsState().value
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                PasswordInputEffect.NavigateToProfileSetup -> navigateToProfileSetup()
            }
        }
    }
    Content(registerState, state, registerViewModel::sendAction, viewModel::sendAction)
}

@Composable
private fun Content(
    registerState: RegisterState,
    state: PasswordInputState,
    onRegisterAction: (RegisterAction) -> Unit,
    onAction: (PasswordInputAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.create_password_text),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.size(16.dp))
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = registerState.password
        ) {
            onRegisterAction(RegisterAction.PasswordChange(it))
            onAction(PasswordInputAction.InputChanged(it))
        }
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            onClick = { onAction(PasswordInputAction.ClickedButton) },
            text = stringResource(R.string.continue_text),
            iconPosition = IconPosition.NONE,
            enable = state.isButtonEnable,
            modifier = Modifier.fillMaxWidth()
        )
    }
}