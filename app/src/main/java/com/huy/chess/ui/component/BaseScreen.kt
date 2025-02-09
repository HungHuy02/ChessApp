package com.huy.chess.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BaseScreen(
    navController: NavController? = null,
    title: String? = null,
    @DrawableRes icon: Int? = null,
    isHomeScreen: Boolean = false,
    showBackIcon: Boolean = false,
    onBackIconClick: () -> Unit = {},
    onRegisterButtonClick: () -> Unit = {},
    onLoginButtonClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            ChessTopAppBar(
                title = title,
                icon = icon,
                isHomeScreen = isHomeScreen,
                showBackIcon = showBackIcon,
                onBackIconClick = onBackIconClick,
                onRegisterButtonClick = onRegisterButtonClick,
                onLoginButtonClick = onLoginButtonClick
            )
        },
        bottomBar = {
            navController?.let {
                ChessBottomAppBar(it)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            content()
        }
    }
}