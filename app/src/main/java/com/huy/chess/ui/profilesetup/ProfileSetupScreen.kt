package com.huy.chess.ui.profilesetup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.profilesetup.composables.ImagePicker
import com.huy.chess.ui.profilesetup.composables.UserNameTextField
import com.huy.chess.viewmodel.ProfileSetupViewModel
import com.huy.chess.viewmodel.RegisterAction
import com.huy.chess.viewmodel.RegisterState
import com.huy.chess.viewmodel.RegisterViewModel

@Composable
fun ProfileSetupScreen(
    registerViewModel: RegisterViewModel,
    viewModel: ProfileSetupViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val registerState = registerViewModel.state.collectAsState().value
    val state = viewModel.state.collectAsState().value
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        registerViewModel.sendAction(RegisterAction.AvatarPathChange(it.toString()))
        viewModel.sendAction(ProfileSetupAction.AvatarPathChanged(it.toString()))
    }
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                ProfileSetupEffect.NavigateToLogin -> navigateToLogin()
                ProfileSetupEffect.OpenImagePicker ->
                    launcher.launch(
                        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
            }
        }
    }
    Content(registerState, state, registerViewModel::sendAction, viewModel::sendAction)
}

@Composable
private fun Content(
    registerState: RegisterState,
    state: ProfileSetupState,
    onRegisterAction: (RegisterAction) -> Unit,
    onAction: (ProfileSetupAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.select_name_text),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(R.string.username_dec_text),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagePicker(
                uri = registerState.avatarPath,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                onAction(ProfileSetupAction.ClickedChangeAvatar)
            }
            UserNameTextField(
                modifier = Modifier.fillMaxWidth(),
                value = registerState.name
            ) {
                onRegisterAction(RegisterAction.NameChange(it))
                onAction(ProfileSetupAction.DisplayNameChanged(it))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append(stringResource(R.string.terms_of_use_p1))
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.terms_of_use_p2))
                }
            }
        )

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                with(registerState) {
                    onAction(ProfileSetupAction.ClickedButton(email, password, name, avatarPath))
                }
            },
            text = stringResource(R.string.create_account_text),
            iconPosition = IconPosition.NONE,
            enable = state.isButtonEnable
        )
    }
}