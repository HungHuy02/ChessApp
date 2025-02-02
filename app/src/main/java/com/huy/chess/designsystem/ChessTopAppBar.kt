package com.huy.chess.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huy.chess.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChessTopAppBar(
    title: String,
    @DrawableRes icon: Int? = null,
    isHomeScreen: Boolean
) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                icon?.let {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "icon"
                    )
                }
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        navigationIcon = {
            if (isHomeScreen) {
                TextButton(onClick = {}) {
                    Text(
                        text = stringResource(R.string.login_text),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        actions = {
            if (isHomeScreen) {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.height(32.dp)
                        .padding(end = 8.dp)
                        .background(color = Color.Gray, shape = MaterialTheme.shapes.small)
                        .padding(top = 0.2.dp)
                        .background(Color.Blue, shape = MaterialTheme.shapes.small)
                        .padding(bottom = 0.8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.register_text),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}