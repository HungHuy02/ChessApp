package com.huy.chess.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.huy.chess.ui.emailinput.EmailInputScreen
import com.huy.chess.ui.login.LoginScreen
import com.huy.chess.ui.passwordinput.PasswordInputScreen
import com.huy.chess.ui.profilesetup.ProfileSetupScreen
import com.huy.chess.ui.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable object Auth
@Serializable object Login
@Serializable object Register
@Serializable object EmailInput
@Serializable object PasswordInput
@Serializable object ProfileSetup

fun NavGraphBuilder.authDestination(
    navController: NavController
) {
    navigation<Auth>(startDestination = Login) {
        composable<Login> {
            LoginScreen()
        }
        composable<Register> {
            RegisterScreen {
                navController.navigate(EmailInput)
            }
        }
        composable<EmailInput> {
            EmailInputScreen {
                navController.navigate(PasswordInput)
            }
        }
        composable<PasswordInput> {
            PasswordInputScreen {
                navController.navigate(ProfileSetup)
            }
        }
        composable<ProfileSetup> {
            ProfileSetupScreen()
        }
    }
}