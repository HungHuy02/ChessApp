package com.huy.chess.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.register.composables.TextWithDivider
import com.huy.chess.ui.theme.ChessTheme

@Composable
fun RegisterScreen(
    navigateToEmailInput: () -> Unit
) {
    BaseScreen(
        showBackIcon = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 60.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                onClick = navigateToEmailInput,
                text = stringResource(R.string.register_with_email_text),
                iconPosition = IconPosition.NONE
            )

            TextWithDivider(modifier = Modifier.fillMaxWidth())

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                text = stringResource(R.string.continue_with_google_text),
                textStyle = MaterialTheme.typography.titleMedium,
                painter = painterResource(R.drawable.google),
                iconPosition = IconPosition.START
            )

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                text = stringResource(R.string.continue_with_facebook_text),
                textStyle = MaterialTheme.typography.titleMedium,
                painter = painterResource(R.drawable.facebook),
                iconPosition = IconPosition.START
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChessTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            RegisterScreen({})
        }
    }

}