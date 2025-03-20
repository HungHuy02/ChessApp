package com.huy.chess.ui.login

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.huy.chess.R
import com.huy.chess.data.auth.AccountManager
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.component.PasswordTextField
import com.huy.chess.ui.login.composables.AccountTextField
import com.huy.chess.ui.login.composables.TextWithFullDivider
import com.huy.chess.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    popBackStack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }
    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {}

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                LoginEffect.NavigateToHome -> navigateToHome()
                LoginEffect.SignInGoogle -> accountManager.signInGoogle()
                LoginEffect.SignInFacebook -> {
                    launcher.launch(listOf("email", "public_profile"))
                    accountManager.signInFacebook(callbackManager, loginManager)
                }
                LoginEffect.PopBackStack -> popBackStack()
            }
        }
    }
    Content(state, viewModel::sendAction)
}

@Composable
private fun Content(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back_24px),
            contentDescription = "icon back",
            modifier = Modifier
                .clickable {
                    onAction(LoginAction.ClickedBackButton)
                }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.login_text),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        AccountTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.account
        ) {
            onAction(LoginAction.AccountChange(it))
        }
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password
        ) {
            onAction(LoginAction.PasswordChange(it))
        }
        TextWithFullDivider()
        AppButton(
            onClick = { onAction(LoginAction.ClickedLoginFacebookButton) },
            text = stringResource(R.string.login_with_facebook_text),
            textStyle = MaterialTheme.typography.titleMedium,
            painter = painterResource(R.drawable.facebook),
            iconPosition = IconPosition.START
        )
        AppButton(
            onClick = { onAction(LoginAction.ClickedLoginGoogleButton) },
            text = stringResource(R.string.login_with_google_text),
            textStyle = MaterialTheme.typography.titleMedium,
            painter = painterResource(R.drawable.google),
            iconPosition = IconPosition.START
        )
        Text(
            text = stringResource(R.string.forgot_password_text),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable {

            }.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.weight(1f))
        AppButton(
            onClick = { onAction(LoginAction.ClickedLoginButton) },
            text = stringResource(R.string.login_text),
            iconPosition = IconPosition.NONE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}