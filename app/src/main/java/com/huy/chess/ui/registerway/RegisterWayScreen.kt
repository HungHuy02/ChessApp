package com.huy.chess.ui.registerway

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.huy.chess.ui.registerway.composables.TextWithDivider
import com.huy.chess.viewmodel.RegisterViewModel
import com.huy.chess.viewmodel.RegisterWayViewModel

@Composable
fun RegisterWayScreen(
    registerViewModel: RegisterViewModel,
    viewModel: RegisterWayViewModel = hiltViewModel(),
    navigateToEmailInput: () -> Unit,
    navigateLogIn: () -> Unit,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }
    val callbackManager = CallbackManager.Factory.create()
    val loginManager = LoginManager.getInstance()
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)) {}

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                RegisterWayEffect.NavigateToEmailInput -> navigateToEmailInput()
                RegisterWayEffect.SignInGoogle -> accountManager.signInGoogle()
                RegisterWayEffect.SignInFacebook -> {
                    launcher.launch(listOf("email", "public_profile"))
                    accountManager.signInFacebook(callbackManager, loginManager)
                }
                RegisterWayEffect.NavigateLogin -> navigateLogIn()
                RegisterWayEffect.PopBackStack -> popBackStack()
            }
        }
    }

    Content(viewModel::sendAction)
}

@Composable
private fun Content(onAction: (RegisterWayAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 60.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back_24px),
                contentDescription = "icon back",
                modifier = Modifier.clickable {
                    onAction(RegisterWayAction.ClickedBack)
                }
            )
            Text(
                text = stringResource(R.string.login_text),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.clickable {
                    onAction(RegisterWayAction.ClickedLogIn)
                }
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.create_your_account_text),
            style = MaterialTheme.typography.titleLarge
        )
        Image(
            painter = painterResource(R.drawable.register_image),
            contentDescription = "register image"
        )
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(RegisterWayAction.RegisterWayWithEmail) },
            text = stringResource(R.string.register_with_email_text),
            iconPosition = IconPosition.NONE
        )

        TextWithDivider(modifier = Modifier.fillMaxWidth())

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(RegisterWayAction.RegisterWayWithGoogle) },
            text = stringResource(R.string.continue_with_google_text),
            textStyle = MaterialTheme.typography.titleMedium,
            painter = painterResource(R.drawable.google),
            iconPosition = IconPosition.START
        )

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(RegisterWayAction.RegisterWayWithFacebook) },
            text = stringResource(R.string.continue_with_facebook_text),
            textStyle = MaterialTheme.typography.titleMedium,
            painter = painterResource(R.drawable.facebook),
            iconPosition = IconPosition.START
        )
    }
}
