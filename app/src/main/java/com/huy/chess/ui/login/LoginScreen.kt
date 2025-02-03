package com.huy.chess.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.designsystem.AppButton
import com.huy.chess.designsystem.IconPosition
import com.huy.chess.ui.login.composables.TextWithFullDivider
import com.huy.chess.ui.login.composables.AccountTextField
import com.huy.chess.designsystem.PasswordTextField

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppButton(
            onClick = {},
            text = stringResource(R.string.login_with_facebook_text),
            textStyle = MaterialTheme.typography.titleMedium,
            painter = painterResource(R.drawable.facebook),
            iconPosition = IconPosition.START
        )
        AppButton(
            onClick = {},
            text = stringResource(R.string.login_with_google_text),
            textStyle = MaterialTheme.typography.titleMedium,
            painter = painterResource(R.drawable.google),
            iconPosition = IconPosition.START
        )
        TextWithFullDivider()
        AccountTextField(modifier = Modifier.fillMaxWidth())
        PasswordTextField(modifier = Modifier.fillMaxWidth())
        Text(
            text = stringResource(R.string.forgot_password_text),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable {

            }
        )
        AppButton(
            onClick = {},
            text = stringResource(R.string.login_text),
            iconPosition = IconPosition.NONE
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LoginScreen()
}