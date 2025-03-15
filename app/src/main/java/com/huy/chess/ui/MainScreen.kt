package com.huy.chess.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.huy.chess.navigation.Login
import com.huy.chess.navigation.Register
import com.huy.chess.navigation.RegisterDialog
import com.huy.chess.navigation.TopLevelDestination
import com.huy.chess.navigation.authDestination
import com.huy.chess.navigation.bottomDestination
import com.huy.chess.navigation.playDestination
import com.huy.chess.ui.component.ChessBottomAppBar
import com.huy.chess.ui.component.ChessTopAppBar
import com.huy.chess.ui.component.FocusClearIme
import com.huy.chess.ui.dialog.register.RegisterDialog

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    FocusClearIme {
        Scaffold(
            topBar = {
                ChessTopAppBar(
                    currentDestination = currentDestination,
                    onLoginButtonClick = { navController.navigate(Login) },
                    onRegisterButtonClick = { navController.navigate(Register) },
                    onBackIconClick = { navController.popBackStack() }
                )
            },
            bottomBar = {
                BottomBar(currentDestination) {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = TopLevelDestination.Home,
                modifier = Modifier.padding(it)
            ) {
                bottomDestination(navController)
                authDestination(navController)
                playDestination(navController)
                dialog<RegisterDialog> {
                    RegisterDialog(
                        navigateRegister = { navController.navigate(Register) }
                    ) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

}

@Composable
private fun BottomBar(
    currentDestination: NavDestination?,
    onClick: (TopLevelDestination) -> Unit
) {
    when (currentDestination?.route) {
        TopLevelDestination.Home::class.qualifiedName,
        TopLevelDestination.Study::class.qualifiedName,
        TopLevelDestination.Puzzles::class.qualifiedName,
        TopLevelDestination.MoreOptions::class.qualifiedName -> {
            ChessBottomAppBar(
                currentDestination = currentDestination,
                onClick = onClick
            )
        }
    }
}