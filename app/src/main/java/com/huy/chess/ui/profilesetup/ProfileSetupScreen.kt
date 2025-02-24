package com.huy.chess.ui.profilesetup

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huy.chess.R
import com.huy.chess.ui.component.AppButton
import com.huy.chess.ui.component.IconPosition
import com.huy.chess.ui.profilesetup.composables.ImagePicker
import com.huy.chess.ui.profilesetup.composables.UserNameTextField

@Composable
fun ProfileSetupScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
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
            ImagePicker(modifier = Modifier.padding(end = 8.dp))
            UserNameTextField(modifier = Modifier.fillMaxWidth())
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
            onClick = {},
            text = stringResource(R.string.create_account_text),
            iconPosition = IconPosition.NONE
        )
    }
}

@Composable
@Preview
fun Preview() {
    ProfileSetupScreen()
}