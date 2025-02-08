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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.designsystem.AppButton
import com.huy.chess.designsystem.IconPosition
import com.huy.chess.ui.emailinput.composables.EmailTextField

@Composable
fun EmailInputScreen(
    navigateToPasswordInput: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Text(
            text = stringResource(R.string.your_email_text),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.size(16.dp))
        EmailTextField(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            onClick = navigateToPasswordInput,
            text = stringResource(R.string.continue_text),
            iconPosition = IconPosition.NONE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun Preview() {
    EmailInputScreen({})
}