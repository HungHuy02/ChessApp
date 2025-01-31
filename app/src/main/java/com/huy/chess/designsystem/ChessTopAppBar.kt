package com.huy.chess.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
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
                Text(text = title)
            }
        },
        navigationIcon = {
            if (isHomeScreen) {
                TextButton(onClick = {}) {
                    Text(text = stringResource(R.string.login_text))
                }
            }
        },
        actions = {
            if (isHomeScreen) {
                AppButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = {},
                    text = stringResource(R.string.register_text),
                    fontSize = 20.sp,
                    textColor = Color.White,
                    iconPosition = IconPosition.NONE
                )
            }
        }

    )
}