package com.huy.chess.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.huy.chess.ui.emailinput.EmailInputScreen
import com.huy.chess.ui.login.LoginScreen
import com.huy.chess.ui.passwordinput.PasswordInputScreen
import com.huy.chess.ui.profilesetup.ProfileSetupScreen
import com.huy.chess.ui.registerway.RegisterWayScreen
import com.huy.chess.viewmodel.RegisterViewModel
import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object Register
@Serializable object RegisterWay
@Serializable object EmailInput
@Serializable object PasswordInput
@Serializable object ProfileSetup

fun NavGraphBuilder.authDestination(
    navController: NavController
) {
    composable<Login> {
        LoginScreen {
            navController.navigate(TopLevelDestination.Home)
        }
    }
    navigation<Register>(startDestination = RegisterWay) {
        composable<RegisterWay> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Register)
            }
            val registerViewModel = hiltViewModel<RegisterViewModel>(parentEntry)
            RegisterWayScreen(
                registerViewModel = registerViewModel
            ) {
                navController.navigate(EmailInput)
            }
        }
        composable<EmailInput> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Register)
            }
            val registerViewModel = hiltViewModel<RegisterViewModel>(parentEntry)
            EmailInputScreen(
                registerViewModel = registerViewModel
            ) {
                navController.navigate(PasswordInput)
            }
        }
        composable<PasswordInput> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Register)
            }
            val registerViewModel = hiltViewModel<RegisterViewModel>(parentEntry)
            PasswordInputScreen(
                registerViewModel = registerViewModel
            ) {
                navController.navigate(ProfileSetup)
            }
        }
        composable<ProfileSetup> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Register)
            }
            val registerViewModel = hiltViewModel<RegisterViewModel>(parentEntry)
            ProfileSetupScreen(
                registerViewModel = registerViewModel
            ) {
                navController.navigate(Login)
            }
        }
    }

}