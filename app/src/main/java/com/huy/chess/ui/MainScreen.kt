package com.huy.chess.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.Login
import com.huy.chess.navigation.Main
import com.huy.chess.navigation.Play
import com.huy.chess.navigation.Profile
import com.huy.chess.navigation.Register
import com.huy.chess.navigation.RegisterDialog
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.playDestination
import com.huy.chess.ui.component.FocusClearIme
import com.huy.chess.ui.dialog.register.RegisterDialog
import com.huy.chess.ui.profile.ProfileScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    FocusClearIme {
        NavHost(
            navController = navController,
            startDestination = Main
        ) {
            composable<Main> {
                BottomNavigationScreen(
                    navigatePlay = { navController.navigate(Play) },
                    navigateLogin = { navController.navigate(Login) },
                    navigateRegister = { navController.navigate(Register) }
                )
            }
            authDestination(navController)
            playDestination(navController)
            dialog<RegisterDialog> {
                RegisterDialog(
                    navigateRegister = { navController.navigate(Register) }
                ) {
                    navController.popBackStack()
                }
            }
            composable<Profile> {
                ProfileScreen(
                    navigateFriends = {},
                    navigateEditProfile = {},
                    navigateGameArchive = {},
                    popBackStack = { navController.popBackStack() }
                )
            }
        }
    }
}