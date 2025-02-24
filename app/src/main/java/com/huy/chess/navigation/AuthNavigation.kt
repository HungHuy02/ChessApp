package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.huy.chess.ui.component.BaseScreen
import com.huy.chess.ui.emailinput.EmailInputScreen
import com.huy.chess.ui.login.LoginScreen
import com.huy.chess.ui.passwordinput.PasswordInputScreen
import com.huy.chess.ui.profilesetup.ProfileSetupScreen
import com.huy.chess.ui.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object Register
@Serializable object EmailInput
@Serializable object PasswordInput
@Serializable object ProfileSetup

fun NavGraphBuilder.authDestination(
    navController: NavController
) {
    composable<Login> {
        BaseScreen(
            showBackIcon = true,
            onBackIconClick = { navController.popBackStack() }
        ) {
            LoginScreen()
        }
    }
    composable<Register> {
        BaseScreen(
            showBackIcon = true,
            onBackIconClick = { navController.popBackStack() }
        ) {
            RegisterScreen {
                navController.navigate(EmailInput)
            }
        }
    }
    composable<EmailInput> {
        BaseScreen(
            showBackIcon = true,
            onBackIconClick = { navController.popBackStack() }
        ) {
            EmailInputScreen {
                navController.navigate(PasswordInput)
            }
        }
    }
    composable<PasswordInput> {
        BaseScreen(
            showBackIcon = true,
            onBackIconClick = { navController.popBackStack() }
        ) {
            PasswordInputScreen {
                navController.navigate(ProfileSetup)
            }
        }
    }
    composable<ProfileSetup> {
        BaseScreen(
            showBackIcon = true,
            onBackIconClick = { navController.popBackStack() }
        ) {
            ProfileSetupScreen()
        }
    }
}